package com.github.leuludyha.ibdb.presentation.components.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WithHeader(
    header: String,
    padding: PaddingValues = PaddingValues(start = 10.dp, top = 10.dp),
    content: (@Composable () -> Unit),
) {
    Text(
        modifier = Modifier.padding(paddingValues = padding),
        text = header,
        style = MaterialTheme.typography.headlineMedium
    )
    content()
}