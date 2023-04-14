package com.github.leuludyha.ibdb.presentation.screen.collection

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.auth.DisplayIfAuthenticated
import com.github.leuludyha.ibdb.presentation.components.reading_list.ReadingList

@Composable
fun CollectionScreen(
    navController: NavHostController,
    padding: PaddingValues,
    viewModel: CollectionScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(padding)
    ) {

        DisplayIfAuthenticated(viewModel.authContext) { authenticatedPrincipal ->
            val userPreferences = authenticatedPrincipal.preferences

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.reading_list_title),
                    style = MaterialTheme.typography.titleMedium
                )
                ReadingList(
                    navController = navController,
                    preferences = userPreferences
                )
            }
        }
    }
}