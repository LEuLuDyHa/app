package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.PagingItemList
import com.github.leuludyha.ibdb.presentation.components.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.search.SearchBar
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues,
    viewModel: BookSearchScreenViewModel = hiltViewModel()
) {
    val queryLoading by viewModel.queryLoading
    val searchedWorks = viewModel.searchedWorks.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect { systemUiController.setStatusBarColor(color = systemBarColor) }


    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SearchBar(
                outerPadding = PaddingValues(top = padding.calculateTopPadding(), start = padding.calculateStartPadding(LayoutDirection.Ltr), end = padding.calculateEndPadding(LayoutDirection.Ltr)),
                onDone = viewModel::searchWorks
            )
        }

        if (queryLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        } else {
            PagingItemList(
                orientation = Orientation.Vertical,
                values = searchedWorks,
                itemMapper = {
                    MiniBookView(
                        work = it,
                        onClick = { clickedWork ->
                            navController.navigate(
                                route = Screen.BookDetails.passBookId(clickedWork.Id())
                            )
                        },
                        orientation = Orientation.Vertical,
                        displaySubjects = true
                    )
                },
            )
        }
    }
}