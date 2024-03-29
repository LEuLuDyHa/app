package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
import com.github.leuludyha.ibdb.presentation.components.search.BookSearchViewModel
import com.github.leuludyha.ibdb.presentation.components.utils.PagingItemList
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues,
    query: String? = null,
    bookSearchViewModel: BookSearchViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect { systemUiController.setStatusBarColor(color = systemBarColor) }

    BookSearch(
        navController = navController,
        outerPadding = padding,
        query = query,
        viewModel = bookSearchViewModel
    ) { queryResult ->
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