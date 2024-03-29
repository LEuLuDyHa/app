package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.leuludyha.ibdb.presentation.components.sharing.SharedWorkListener
import com.github.leuludyha.ibdb.presentation.screen.HomeScreen
import com.github.leuludyha.ibdb.presentation.screen.author_views.AuthorDetailsScreen
import com.github.leuludyha.ibdb.presentation.screen.book_details.BookDetailsScreen
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreen
import com.github.leuludyha.ibdb.presentation.screen.profile.ProfileScreen
import com.github.leuludyha.ibdb.presentation.screen.profile.UserProfile
import com.github.leuludyha.ibdb.presentation.screen.search.BookSearchScreen
import com.github.leuludyha.ibdb.presentation.screen.search.barcode.BarcodeScreen
import com.github.leuludyha.ibdb.presentation.screen.share.ShareScreen
import com.github.leuludyha.ibdb.util.Constant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomToolbar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(route = Screen.Profile.route) {
                ProfileScreen(padding)
            }
            composable(route = Screen.Home.route) {
                HomeScreen(navController, padding)
            }
            composable(route = Screen.BarcodeScan.route) {
                BarcodeScreen(navController, padding)
            }
            composable(
                route = Screen.BookSearch.route,
                arguments = listOf(navArgument(Constant.SEARCH_QUERY_ARGUMENT_KEY) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                backStackEntry.arguments
                    ?.getString(Constant.SEARCH_QUERY_ARGUMENT_KEY)
                    .let { arg ->
                        var query: String? = null
                        if (arg != "{${Constant.SEARCH_QUERY_ARGUMENT_KEY}}" && arg != null) {
                            query = arg
                        }
                        BookSearchScreen(
                            navController, padding, query = query
                        )
                    }
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
            composable(
                route = Screen.AuthorDetails.route,
                arguments = listOf(navArgument(Constant.AUTHOR_DETAILS_ARGUMENT_KEY) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                backStackEntry.arguments
                    ?.getString(Constant.AUTHOR_DETAILS_ARGUMENT_KEY)
                    ?.let { authorId ->
                        AuthorDetailsScreen(navController, padding, authorId)
                    }
            }
            composable(route = Screen.FindBook.route) {
                // TODO Add find book screen here
            }
            composable(route = Screen.UserProfile.route) {
                UserProfile(navController, padding)
            }
            composable(route = Screen.GoogleMaps.route) {
                //We haven't used padding values until now, but without them, the navBar and google maps overlap onto each other.
                GoogleMapsScreen(
                    paddingValues = padding
                )
            }
            composable(
                route = Screen.Share.route,
                arguments = listOf(navArgument(Constant.SHARE_BOOK_ID_ARGUMENT_KEY) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                backStackEntry.arguments
                    ?.getString(Constant.SHARE_BOOK_ID_ARGUMENT_KEY)
                    ?.let { workId -> ShareScreen(navController, padding, workId) }
            }
            composable(route = Screen.ReceiveNearbyWork.route) {
                //TODO: Add Hana's screen once it is finished
                SharedWorkListener(navController = navController)
            }
        }
    }
}