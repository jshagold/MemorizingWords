package com.example.memorizingwords.navigation

import androidx.navigation.NavController

fun NavController.navigateToAddJapanese() {
    this.navigate(Route.ADD_JAPANESE)
}

fun NavController.navigateToWordList() {
    this.navigate(Route.WORD_LIST)
}

fun NavController.navigateToWordDetail(wordId: Long) {
    this.navigate("${Route.WORD_DETAIL}/$wordId")
}