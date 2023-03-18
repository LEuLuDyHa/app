package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.leuludyha.ibdb.presentation.screen.auth.signin.SignInScreen
import com.github.leuludyha.ibdb.presentation.screen.home.HomeScreen
import com.github.leuludyha.ibdb.util.Constant

// TODO some kind of linking between NavGraph and BottomToolbar

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {
        composable(route = Screen.SignIn.route) {
            SignInScreen()
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.BookSearch.route) {
            // TODO Add book search screen composable here
        }
        composable(
            route = Screen.BookDetails.route,
            arguments = listOf(navArgument(Constant.BOOK_DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Constant.BOOK_DETAILS_ARGUMENT_KEY)
                ?.let { /* TODO Add book details screen composable here, take argument "it" too */ }
        }
    }
}