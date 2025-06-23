package com.example.memorizingwords.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.media3.exoplayer.ExoPlayer


@Composable
fun rememberExoPlayer(context: Context): ExoPlayer {
    return remember {
        ExoPlayer.Builder(context).build()
    }.also {
        DisposableEffect(Unit) {
            onDispose { it.release() }
        }
    }
}