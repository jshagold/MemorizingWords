package com.example.memorizingwords.navigation

import androidx.navigation.NavController

fun NavController.navigateToAddJapanese() {
    this.navigate(Route.ADD_JAPANESE)
}

fun NavController.navigateToWordList() {
    this.navigate(Route.WORD_LIST)
}

fun NavController.navigateToRandomWord() {
    this.navigate(Route.RANDOM_WORD)
}

fun NavController.navigateToWordDetail(wordId: Long) {
    this.navigate("${Route.WORD_DETAIL}/$wordId")
}

fun NavController.navigateToModifyWordData(wordId: Long) {
    this.navigate("${Route.MODIFY_WORD}/$wordId")
}

fun NavController.navigateToDrawLetter(letter: String) {
    this.navigate("${Route.DRAW_LETTER}/$letter")
}



fun NavController.navigateToTTSPlayer() {
    this.navigate(Route.TTS_PLAYER)
}