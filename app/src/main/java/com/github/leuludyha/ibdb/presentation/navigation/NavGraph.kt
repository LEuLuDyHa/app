package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.leuludyha.ibdb.presentation.screen.auth.signin.SignInScreen
import com.github.leuludyha.ibdb.presentation.screen.HomeScreen
import com.github.leuludyha.ibdb.presentation.screen.barcode.BarcodeScreen
import com.github.leuludyha.ibdb.presentation.screen.book_details.BookDetailsScreen
import com.github.leuludyha.ibdb.presentation.screen.collection.CollectionScreen
import com.github.leuludyha.ibdb.presentation.screen.profile.ProfileScreen
import com.github.leuludyha.ibdb.presentation.screen.search.BookSearchScreen
import com.github.leuludyha.ibdb.util.Constant

// TODO some kind of linking between NavGraph and BottomToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController) {


    Scaffold(
        bottomBar = { BottomToolbar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SignIn.route
        ) {
            composable(route = Screen.SignIn.route) {
                SignInScreen(navController, padding)
            }
            composable(route = Screen.Profile.route) {
                ProfileScreen(navController, padding)
            }
            composable(route = Screen.Profile.route) {
                ProfileScreen(navController, padding)
            }
            composable(route = Screen.Home.route) {
                HomeScreen(navController, padding)
            }
            composable(route = Screen.BarcodeScan.route) {
                BarcodeScreen(navController, padding)
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
                backStackEntry.arguments
                    ?.getString(Constant.BOOK_DETAILS_ARGUMENT_KEY)
                    ?.let { workId ->
                        BookDetailsScreen(
                            navController, padding, workId
                        )
                    }
            }
            composable(route = Screen.Collection.route) {
                CollectionScreen(navController, padding)
            }
            composable(route = Screen.FindBook.route) {
                // TODO Add find book screen here
            }
        }
    }
}