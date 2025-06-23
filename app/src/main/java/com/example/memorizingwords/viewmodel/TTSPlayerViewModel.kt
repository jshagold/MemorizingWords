package com.example.memorizingwords.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.core.result.Result
import com.example.domain.repository.usecase.DownloadTTSFileUseCase
import com.example.memorizingwords.state.PlayerUiState
import com.example.memorizingwords.state.TTSPlayerScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


@HiltViewModel
class TTSPlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val downloadTTSFileUseCase: DownloadTTSFileUseCase,
) : ViewModel() {


    private val testTextList1 = listOf(
        "테스트 이윽고 그의 침대가 삐걱거렸다. 그러더니, 벽 너머로 이상한 소리가 희미하게 새어 나왔다. 나는 그가 울고 있다는 것을 깨달았다. 그때 왜 엄마 생각이 났는지 모르겠다.",
        "테스트 1",
        "테스트 2",
        "테스트 3",
        "테스트 4",
        "테스트 5",
        "테스트 6",
    )

    private val testTextList2 = listOf(
        "일부 기기 구성은 런타임에 변경될 수 있다.(기기의 화면 회전이나, 언어 설정 등의 이유) 이러한 변경이 일어나는 경우 Android는 실행 중인 Activity를 다시 시작한다. (onDestroy()가 호출되고, 그다음에 onCreate()가 호출됨)",
        "반대로 Configuration change가 변경됐을 때에는 Activity의 onDestroy()가 호출되고, 그다음에 onCreate()가 호출되기 때문에 ViewModel 인스턴스 유지를 위해 ViewModelStore의 clear()는 실행되지 않는다.",
        "isChangingConfigurations()가 false 일 경우에 아까 살펴본 ViewModelStore의 clear() 메서드가 호출되며 생성된 모든 ViewModel이 제거된다.",
        "현재 재생 상태가 변할 때 라던지, 스트림이 끊기거나 잘못되서 에러가 발생하면 Listener 에 state 가 Int 형태로 전달된다. 에러가 났을 때 다시 재접속 시도를 한다던가.. 소스 자체에 문제가 생겼을 때 유저에게 노티를 한다던가.. 유용한 기능이다.",
        "아무튼 위에 작성해둔 코드대로 작성하면 PlayerView 에 Exoplayer 가 붙는다."
    )

    private var downTextResource = testTextList1

    private var downloadFileJob: Job? = null

    private val mediaItemMap = sortedMapOf<Int, MediaItem>()
    private val player = ExoPlayer.Builder(context).build()

    private var _uiState: MutableStateFlow<TTSPlayerScreenState> = MutableStateFlow(TTSPlayerScreenState())
    val uiState: StateFlow<TTSPlayerScreenState> = _uiState.asStateFlow()


    init {
        Napier.e { "init" }
        changeTextList()

        player.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if(isPlaying) {

                    } else {

                    }
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    when(playbackState) {
                        Player.STATE_IDLE -> {
                            Napier.d { "Playback Idle" }
                            player.seekToDefaultPosition(0)
                        }
                        Player.STATE_BUFFERING -> {
                            Napier.d { "Playback Buffering" }

                        }
                        Player.STATE_READY -> {
                            Napier.d { "Playback Ready" }

                        }
                        Player.STATE_ENDED -> {
                            Napier.d { "Playback End" }

                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)


                }
            }
        )
    }

    private fun startFileDownload() {
        downloadFileJob?.cancel()

        downloadFileJob = viewModelScope.launch {
            mediaItemMap.clear()

            downloadTTSFileUseCase(context.filesDir, downTextResource)
                .onStart {  }
                .onCompletion {

                }
                .collect { result: Result<Pair<Int, String>> ->
                    when(result) {
                        is Result.Failure -> {
                            //todo
                            Napier.e { "Download File Flow Failure msg: ${result.message}" }
                        }
                        is Result.Success -> {
                            val fileIndex = result.data.first
                            val fileName = result.data.second

                            val realFile = File(context.filesDir, fileName)
                            val uri = Uri.fromFile(realFile)
                            mediaItemMap[fileIndex] = MediaItem.fromUri(uri)
                            Napier.d { "Download File: ${fileName}" }
                        }
                    }
                }
        }
    }

    fun changeTextList() {
        downTextResource = if(downTextResource.size < 7) {
            testTextList1
        } else {
            testTextList2
        }
        viewModelScope.launch(Dispatchers.IO) {
            Napier.e { "change Text List" }
            startFileDownload()
        }
    }


    fun onPlay() {
        _uiState.update {
            it.copy(
                playerState = PlayerUiState.Playing
            )
        }

        val sortedList = mediaItemMap.values.toList()
        player.setMediaItems(sortedList)

        player.prepare()
        player.play()
    }

    fun onPause() {
        _uiState.update {
            it.copy(
                playerState = PlayerUiState.Pause
            )
        }


        player.pause()
    }

    fun onStop() {
        _uiState.update {
            it.copy(
                playerState = PlayerUiState.Idle
            )
        }


        player.stop()
        player.seekToDefaultPosition(0)
    }

    fun onPrev() {
        _uiState.update {
            it.copy(
                playerState = PlayerUiState.Idle
            )
        }


        player.seekToPrevious()
    }

    fun onNext() {
        _uiState.update {
            it.copy(
                playerState = PlayerUiState.Idle
            )
        }

        player.seekToNext()
    }
}