package com.github.leuludyha.ibdb.presentation.screen.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.utils.ButtonWithIcon
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

@Composable
fun CollectionScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        ButtonWithIcon(
            onClick = { /*TODO*/ },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(id = R.string.books_read_button_lbl),
            trailingIcon = Icons.Filled.NavigateNext,
        )
        ButtonWithIcon(
            onClick = { /*TODO*/ },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(id = R.string.reading_list_button_lbl),
            trailingIcon = Icons.Filled.NavigateNext,
        )
        ButtonWithIcon(
            onClick = { /*TODO*/ },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            text = stringResource(id = R.string.works_on_sale_button_lbl),
            trailingIcon = Icons.Filled.NavigateNext,
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    IBDBTheme {
        CollectionScreen(
            navController = rememberNavController(),
            padding = PaddingValues(0.dp)
        )
    }
}