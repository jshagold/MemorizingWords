package com.example.memorizingwords.state

data class TTSPlayerScreenState(
    val playerState: PlayerUiState = PlayerUiState.Idle,
    val playingIndex: Int = 0,
)