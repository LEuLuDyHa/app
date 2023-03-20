package com.github.leuludyha.ibdb.presentation.screen.home

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.ibdb.presentation.components.Orientation
import com.github.leuludyha.ibdb.presentation.components.WorkList
import com.github.leuludyha.ibdb.ui.navigation.BottomToolbar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// TODO REMOVE
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val (query, setQuery) = remember { mutableStateOf("") }
    val (works, setWorks) = remember { mutableStateOf<List<Work>?>(null) }
    val (queryLoading, setQueryLoading) = remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(color = systemBarColor)
    }

    Scaffold(
        topBar = {
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
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                setQueryLoading(true)
                                viewModel.getAllBooks(query) { works ->
                                    setWorks(works)
                                    setQueryLoading(false)
                                }
                                true
                            }
                            false
                        }
                )
            }
        },
        bottomBar = { BottomToolbar() },
        content = { paddingValues ->
            if (queryLoading) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            } else {
                works?.let {
                    WorkList(
                        orientation = Orientation.Vertical,
                        works = it,
                        navController = navController,
                        paddingValues = paddingValues
                    )

                }
            }
        }
    )
}