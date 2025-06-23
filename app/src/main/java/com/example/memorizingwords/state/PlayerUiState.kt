package com.example.memorizingwords.state

import androidx.media3.common.Player

sealed class PlayerUiState {
    data object Loading: PlayerUiState()
    data object Idle: PlayerUiState()
    data object Playing: PlayerUiState()
    data object Pause: PlayerUiState()
}