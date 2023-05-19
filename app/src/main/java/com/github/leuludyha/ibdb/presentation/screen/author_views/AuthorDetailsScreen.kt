package com.github.leuludyha.ibdb.presentation.screen.author_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.ibdb.presentation.components.books.author_views.FullAuthorView

@Composable
fun AuthorDetailsScreen(
    navController: NavHostController,
    padding: PaddingValues,
    authorId: String,
    viewModel: AuthorDetailsScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    // Go fetch the author based on its author id
    SideEffect{ viewModel.loadAuthorFrom(context, authorId) }

    // Collect the resulting work as a state
    val authorResult by viewModel.author.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        when (authorResult) {
            // When success, display the full book view
            is Result.Success -> {
                (authorResult as Result.Success<Author>).data?.let {
                    FullAuthorView(
                        navController = navController,
                        author = it
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
                        text = (authorResult as Result.Error<Author>).message ?: "Not Found",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            // Otherwise display loading
            else -> LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("progressIndicator")
            )
        }
    }
}