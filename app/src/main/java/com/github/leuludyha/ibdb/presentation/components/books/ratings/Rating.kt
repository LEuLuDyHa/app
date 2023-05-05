package com.github.leuludyha.ibdb.presentation.components.books.ratings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.math.MathUtils.clamp
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

enum class RatingSize {
    Small,
    Default,
}

/**
 * Create a Rating component which displays a rating of a book with five stars
 * @param size The size of the rating component, Default is [RatingSize.Default]
 * @param value The value from 0 (Very bad) to 1 (Very good) of the rating
 * @param onChange Handles a touch on the rating component, if left null, then the component
 * isn't interactable
 *
 */
@Composable
fun Rating(
    value: Float,
    onChange: ((Float) -> Unit)? = null,
    size: RatingSize = RatingSize.Default,
    label: String? = null,
) {
    when (size) {
        RatingSize.Default -> RatingMedium(value, onChange, label)
        RatingSize.Small -> RatingSmall(value, onChange)
    }
}

@Composable
private fun RatingMedium(
    value: Float,
    onChange: ((Float) -> Unit)? = null,
    label: String? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium
            )
        }
        RatingSmall(value = value, onChange = onChange)
        Text(
            text = "${"%.1f".format(value)}/5",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun RatingSmall(
    value: Float,
    onChange: ((Float) -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        (0 until 5).map { i ->
            // Collects the size of the background when it is mounted on the view
            val (iconSize, setIconSize) = remember { mutableStateOf(Pair(0f, 0f)) }

            val factor = clamp(value - i, 0f, 1f)

            val onChangeI: (() -> Unit)? = if (onChange == null) null else {
                { onChange((i + 1).toFloat()) }
            }

            val gradient = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.scrim,
                ),
                start = Offset(factor * iconSize.first, 0f),
                end = Offset(factor * iconSize.first + .1f, 0f),
                tileMode = TileMode.Clamp
            )

            Icon(
                modifier = Modifier
                    .graphicsLayer(alpha = 0.99f)
                    .optionalClick(onChangeI)
                    .onGloballyPositioned {
                        // Save background size when mounted
                        setIconSize(Pair(it.size.width.toFloat(), it.size.height.toFloat()))
                    }
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.SrcAtop)
                        }
                    },
                imageVector = Icons.Default.StarRate,
                contentDescription = null
            )
        }
    }
}

/**
 * Helper function to remove visual effects in case
 * the click cannot be handled
 */
private fun Modifier.optionalClick(
    onChange: (() -> Unit)? = null,
): Modifier {
    if (onChange == null) {
        return this; }
    return this.clickable { onChange() }
}

@Preview
@Composable
private fun DefaultPreview() {
    IBDBTheme {
        Rating(
            value = 3.5f,
            onChange = { Log.i("Rating", it.toString()) },
            size = RatingSize.Small,
            label = "My Rating"
        )
    }
}