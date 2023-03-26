package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.components.Orientation
import com.github.leuludyha.ibdb.presentation.components.WorkList
import com.github.leuludyha.ibdb.presentation.components.search.BookSearch

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    BookSearch(navController = navController, outerPadding = padding) { queryResult ->
        WorkList(
            orientation = Orientation.Vertical,
            works = queryResult,
            navController = navController,
            paddingValues = padding
        )
    }
}