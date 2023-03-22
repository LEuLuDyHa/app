package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.leuludyha.ibdb.presentation.screen.search.BookSearchScreen
import com.github.leuludyha.ibdb.ui.navigation.BottomToolbar
import com.github.leuludyha.ibdb.util.Constant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomToolbar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.BookSearch.route
        ) {
            composable(route = Screen.Home.route) {
                // TODO Add home screen composable here
            }
            composable(route = Screen.BookSearch.route) {
                BookSearchScreen(navController, padding)
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
}