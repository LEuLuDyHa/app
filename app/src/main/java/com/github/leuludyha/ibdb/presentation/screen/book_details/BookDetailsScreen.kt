package com.github.leuludyha.ibdb.presentation.screen.book_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.components.book_views.FullBookView

@Composable
fun BookDetailsScreen(
    navController: NavHostController,
    padding: PaddingValues,
    workId: String,
    viewModel: BookDetailsScreenViewModel = hiltViewModel()
) {
    // Go fetch the work based on its work id
    SideEffect { viewModel.loadWorkFrom(workId) }

    // Collect the resulting work as a state
    val workResult by viewModel.work.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        when (workResult) {
            // When success, display the full book view
            is Result.Success -> {
                (workResult as Result.Success<Work>).data?.let {
                    FullBookView(
                        navController = navController,
                        work = it
                    )
                } ?: Text(text = "Error")
            }
            // When error, display error
            is Result.Error -> {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = (workResult as Result.Error<Work>).message ?: "Not Found",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            // Otherwise display loading
            else -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}