package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memorizingwords.ui.components.rememberExoPlayer
import com.example.memorizingwords.viewmodel.TTSPlayerViewModel


@Composable
fun TTSPlayerRoute(
    modifier: Modifier = Modifier,
    viewModel: TTSPlayerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context)


    TTSPlayerScreen(
        modifier = modifier,
        onPlay = viewModel::onPlay,
        onPause = viewModel::onPause,
        onStop = viewModel::onStop,
        onNext = viewModel::onNext,
        onPrev = viewModel::onPrev,
        onNextPage = viewModel::changeTextList
    )
}


@Composable
fun TTSPlayerScreen(
    modifier: Modifier = Modifier,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onNextPage: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = onPlay
        ) {
            Text("play")
        }

        Row {
            Button(
                onClick = onPrev
            ) {
                Text("Prev")
            }

            Button(
                onClick = onStop
            ) {
                Text("Stop")
            }

            Button(
                onClick = onPause
            ) {
                Text("Pause")
            }

            Button(
                onClick = onNext
            ) {
                Text("Next")
            }
        }



        Button(
            onClick = onNextPage
        ) {
            Text("Next Page")
        }




    }




}