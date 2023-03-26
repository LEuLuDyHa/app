package com.github.leuludyha.ibdb.presentation.components.search

import android.Manifest
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
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

            val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

            Button(
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
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
