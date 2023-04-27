package com.github.leuludyha.ibdb.presentation.components.books.ratings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

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
                style = MaterialTheme.typography.headlineMedium
            )
        }
        RatingSmall(value = value, onChange = onChange)
        Text(
            text = "${"%.1f".format(value)}/5",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun RatingSmall(
    value: Float,
    onChange: ((Float) -> Unit)? = null,
) {
    // TODO
}