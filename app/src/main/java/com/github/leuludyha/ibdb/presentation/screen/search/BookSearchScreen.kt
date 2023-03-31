package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.PagingItemList
import com.github.leuludyha.ibdb.presentation.components.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect { systemUiController.setStatusBarColor(color = systemBarColor) }

    BookSearch(navController = navController, outerPadding = padding) { queryResult ->
        PagingItemList(
            orientation = Orientation.Vertical,
            values = queryResult,
        ) { work ->
            MiniBookView(
                work = work,
                onClick = { clickedWork ->
                    navController.navigate(
                        route = Screen.BookDetails.passBookId(clickedWork.Id())
                    )
                },
                orientation = Orientation.Horizontal,
                displaySubjects = true
            )
        }
    }
}