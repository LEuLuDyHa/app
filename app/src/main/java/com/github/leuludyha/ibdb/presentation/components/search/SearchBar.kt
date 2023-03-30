package com.github.leuludyha.ibdb.presentation.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Simple search bar Composable executing the given actions.
 *
 * @param onValueChange a callback that is called whenever the text is modified
 * @param onDone a callback that is called whenever the text is confirmed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    outerPadding: PaddingValues,
    onValueChange: (String) -> Unit = { },
    onDone: (String) -> Unit = { },
    viewModel: SearchBarViewModel = hiltViewModel(),
) {
    val searchQuery by viewModel.searchQuery

    Column(
        modifier = Modifier.padding(outerPadding)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                viewModel.updateSearchQuery(it)
                onValueChange(it)
            },
            singleLine = true,
            modifier = Modifier
                .testTag("search_bar::search_field")
                .background(MaterialTheme.colorScheme.secondaryContainer),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onDone(searchQuery) })
        )
    }
}