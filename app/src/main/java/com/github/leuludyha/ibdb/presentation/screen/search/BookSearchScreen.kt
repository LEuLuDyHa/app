package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
import com.github.leuludyha.ibdb.presentation.navigation.Screen

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    BookSearch(outerPadding = padding) { queryResult ->
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