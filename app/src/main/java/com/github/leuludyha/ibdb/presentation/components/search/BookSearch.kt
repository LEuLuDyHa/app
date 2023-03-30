package com.github.leuludyha.ibdb.presentation.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.util.Constant

/**
 * This composable will instantiate a search bar and a scanning button next to it. Whenever a new search
 * is received either from the search bar or the barcode scanning it will call the database, and
 * afterwards the lambda onBooksFoundContent with the results of the search.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearch(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: BookSearchViewModel = hiltViewModel(),
    onBooksFoundContent: @Composable (LazyPagingItems<Work>) -> Unit,
) {

    val queryLoading by viewModel.queryLoading
    val isReadingISBN by viewModel.isReadingISBN
    val searchQuery by viewModel.searchQuery
    val foundWorks = viewModel.searchedWorks.collectAsLazyPagingItems()

    val barcodeReadingResultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>(Constant.BARCODE_RESULT_KEY)?.observeAsState()

    barcodeReadingResultState?.value?.let {
        //This condition is meant to avoid this being called multiple times. It is a workaround and not
        //a fix of the main problem, since I couldn't find a fix. Moreover it looks like this behavior is not even deterministic.
        if(!isReadingISBN) {
            viewModel.updateIsReadingISBN(true)
            viewModel.searchWorks(it)
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<String>(Constant.BARCODE_RESULT_KEY)
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                singleLine = true,
                modifier = Modifier
                    .testTag("book_search::search_field")
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.searchWorks(searchQuery)
                })
            )

            Button(
                modifier = Modifier
                    .testTag("book_search::barcode_scan_button"),
                onClick = {
                    navController.navigate(Screen.BarcodeScan.route)
                }
            ) {
                Text(text = "Scanner")
            }
        }
        if (queryLoading) {
            Column(
                modifier = Modifier
                    .testTag("book_search::query_loading_indicator")
                    .padding(outerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        // If a list of books is found by the query,
        // display the component given as arg while providing it
        // with the result of the query
        AnimatedVisibility(visible = foundWorks.itemCount != 0) {
            onBooksFoundContent(foundWorks)
        }
    }
}
