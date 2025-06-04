package com.example.memorizingwords.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorizingwords.TemporaryScreen
import com.example.memorizingwords.ui.screen.AddJapaneseWordRoute
import com.example.memorizingwords.ui.screen.DrawLetterRoute
import com.example.memorizingwords.ui.screen.ModifyWordDataRoute
import com.example.memorizingwords.ui.screen.RandomWordRoute
import com.example.memorizingwords.ui.screen.WordDetailRoute
import com.example.memorizingwords.ui.screen.WordListRoute

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.MAIN,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        composable(route = Route.MAIN) {
            TemporaryScreen(
                navigateToWordList = {
                    navController.navigateToWordList()
                },
                navigateToAddJapanese = {
                    navController.navigateToAddJapanese()
                },
                navigateToRandomWord = {
                    navController.navigateToRandomWord()
                }
            )
        }

        composable(route = Route.ADD_JAPANESE) {
            AddJapaneseWordRoute()
        }

        composable(route = Route.WORD_LIST) {
            WordListRoute(
                navigateToWordDetail = { wordId: Long ->
                    navController.navigateToWordDetail(wordId)
                }
            )
        }

        composable(route = Route.RANDOM_WORD) {
            RandomWordRoute()
        }

        composable(route = "${Route.WORD_DETAIL}/{wordId}") {
            WordDetailRoute(
                onBackScreen = { navController.popBackStack() },
                navigateToDrawLetter = { navController.navigateToDrawLetter(it) },
                navigateToModifyWordData = { navController.navigateToModifyWordData(it) }
            )
        }

        composable(route = "${Route.MODIFY_WORD}/{wordId}") {
            ModifyWordDataRoute(
                onBackScreen = { navController.popBackStack() },
            )
        }

        composable(route = "${Route.DRAW_LETTER}/{letter}") { navBackStackEntry ->
            val letter: String? = navBackStackEntry.savedStateHandle["letter"]
            if(letter == null) {
                navController.popBackStack()
            } else {
                DrawLetterRoute(
                    letter = letter
                )
            }
        }
    }
}