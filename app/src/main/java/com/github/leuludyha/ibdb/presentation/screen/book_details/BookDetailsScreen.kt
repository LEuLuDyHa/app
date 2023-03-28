package com.github.leuludyha.ibdb.presentation.screen.book_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.components.authentication.DisplayIfAuthenticated

@Composable
fun BookDetailsScreen(
    navController: NavHostController,
    padding: PaddingValues,
    workId: String,
    viewModel: BookDetailsScreenViewModel = hiltViewModel()
) {
    val (work, setWork) = remember { mutableStateOf<Work?>(null) }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        if (work == null) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            Text(text = work.title.orEmpty(), style = MaterialTheme.typography.titleLarge)

            DisplayIfAuthenticated(viewModel.authContext, fallback = { /* Nothing displayed */ }) {
                val userPreferences = it.preferences


            }
        }
    }
}