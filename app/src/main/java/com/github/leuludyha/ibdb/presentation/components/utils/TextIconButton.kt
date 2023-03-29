package com.github.leuludyha.ibdb.presentation.components.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

/** The position of the icon within the button */
enum class IconPosition {
    Start, End
}

/**
 * A button with text and an icon, either positioned at the end or the start of
 * the button.
 * @param icon The icon displayed within the button
 * @param iconPosition The position of the icon within the button
 * @param text The text displayed within the button
 * @param onClick The function to run on a click of the button
 * @param contentColor The color of the content (Text and icon)
 * @param containerColor The color of the container (The button itself)
 * See [DefaultPreview] for a better visualization
 */
@Composable
fun TextIconButton(
    icon: ImageVector,
    iconPosition: IconPosition = IconPosition.End,
    text: String,
    onClick: () -> Unit,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    Button(
        onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = containerColor
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        // Position icon at the start
        if (iconPosition == IconPosition.Start) {
            Icon(icon, text, modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text)
        // Position icon at the end
        if (iconPosition == IconPosition.End) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(icon, text, modifier = Modifier.size(ButtonDefaults.IconSize))
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    IBDBTheme {
        TextIconButton(
            icon = Icons.Filled.Vaccines,
            text = "My Awesome Button",
            onClick = { /* Nothing */ }
        )
    }
}