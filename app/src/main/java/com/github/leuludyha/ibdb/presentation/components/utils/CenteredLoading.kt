package com.github.leuludyha.ibdb.presentation.components.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun CenteredLoading(
    modifier: Modifier = Modifier,
    label: String? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        label?.let {
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .testTag("centeredLoading::label"),
                text = label,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Loading()
    }
}