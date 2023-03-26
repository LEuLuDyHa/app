package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
import com.github.leuludyha.ibdb.presentation.navigation.Screen

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    BookSearch(navController = navController, outerPadding = padding) { queryResult ->
        ItemList(
            orientation = Orientation.Vertical,
            values = queryResult,
        ) { work ->
            MiniBookView(
                work = work,
                onClick = { clickedWork ->
                    navController.navigate(
                        route = Screen.BookDetails.passBookId(clickedWork.getId())
                    )
                },
                orientation = Orientation.Horizontal,
                displaySubjects = true
            )
        }
    }
}