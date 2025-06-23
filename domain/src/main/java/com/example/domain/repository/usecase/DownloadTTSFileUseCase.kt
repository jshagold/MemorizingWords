package com.example.domain.repository.usecase

import com.example.core.result.Result
import com.example.domain.repository.repository.TTSFile
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class DownloadTTSFileUseCase @Inject constructor(
    private val ttsFile: TTSFile
) {
    operator fun invoke(baseDir: File, textList: List<String>): Flow<Result<Pair<Int, String>>> {
        return ttsFile.downloadVoiceFile(baseDir, textList)
    }
}