package com.github.leuludyha.ibdb.presentation.components.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.github.leuludyha.ibdb.R

@Composable
fun ButtonWithIcon(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    text: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leadingIcon?.let {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = stringResource(id = R.string.trailing_icon)
                )
            }
            Text(text = text)
            trailingIcon?.let {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = stringResource(id = R.string.trailing_icon)
                )
            }
        }
    }
}