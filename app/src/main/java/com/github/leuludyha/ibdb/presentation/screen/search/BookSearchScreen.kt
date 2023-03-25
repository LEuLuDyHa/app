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
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.leuludyha.ibdb.presentation.components.Orientation
import com.github.leuludyha.ibdb.presentation.components.WorkList
import com.github.leuludyha.ibdb.presentation.components.search.SearchBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BookSearchScreen(
    navController: NavHostController,
    padding: PaddingValues,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery
    val queryLoading by viewModel.queryLoading
    val searchedWorks = viewModel.searchedWorks.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect { systemUiController.setStatusBarColor(color = systemBarColor) }

    Column(
        modifier = Modifier.padding(padding)
    ) {
        SearchBar(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            onDone = { viewModel.searchWorks(searchQuery) }
        )
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
            WorkList(
                orientation = Orientation.Vertical,
                works = searchedWorks,
                navController = navController,
            )
        }
    }
}