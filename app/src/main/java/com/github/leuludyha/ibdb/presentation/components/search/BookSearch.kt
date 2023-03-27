package com.github.leuludyha.ibdb.presentation.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.util.Constant
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearch(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: BookSearchViewModel = hiltViewModel(),
    onBooksFoundContent: @Composable (List<Work>) -> Unit,
) {
    val (query, setQuery) = remember { mutableStateOf("") }
    val (works, setWorks) = remember { mutableStateOf<List<Work>?>(null) }
    val (queryLoading, setQueryLoading) = remember { mutableStateOf(false) }
    val (readISBN, setReadISBN) = remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    val barcodeReadingResultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>(Constant.BARCODE_RESULT_KEY)?.observeAsState()

    barcodeReadingResultState?.value?.let {
        //This condition is meant to avoid this being called multiple times. It is a workaround and not
        //a fix of the main problem, since I couldn't find a fix. Moreover it looks like this behavior is not even deterministic.
        if(!readISBN) {
            setQuery(it)
            setReadISBN(true)
            setQueryLoading(true)
            viewModel.getAllBooksByISBN(query) { works ->
                setWorks(works)
                setQueryLoading(false)
            }
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<String>(Constant.BARCODE_RESULT_KEY)
        }
    }

    SideEffect { systemUiController.setStatusBarColor(color = systemBarColor) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { setQuery(it) },
                singleLine = true,
                modifier = Modifier
                    .testTag("book_search::search_field")
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    // Set query loading animation on query begin
                    setQueryLoading(true)
                    viewModel.getAllBooks(query) { works ->
                        setWorks(works)
                        // Cancel query loading animation on query resolution
                        setQueryLoading(false)
                    }
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
        AnimatedVisibility(visible = works != null) {
            works?.let { onBooksFoundContent(works) }
        }
    }
}
