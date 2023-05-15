package com.github.leuludyha.ibdb.presentation.components.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.ibdb.presentation.Orientation

@Composable
fun <T : Keyed> ItemList(
    modifier: Modifier = Modifier,
    values: List<T>,
    orientation: Orientation = Orientation.Horizontal,
    itemMapper: @Composable (t: T) -> Unit,
) {
    val content: LazyListScope.() -> Unit = {
        items(
            items = values,
            key = { it.Id() }
        ) { itemMapper(it) }
    }

    Surface {
        when (orientation) {
            Orientation.Vertical -> ItemColumn(content, modifier)
            Orientation.Horizontal -> ItemRow(content, modifier)
        }
    }
}

private val padding: PaddingValues = PaddingValues(
    horizontal = 8.dp,
    vertical = 4.dp,
)

@Composable
private fun ItemColumn(
    content: LazyListScope.() -> Unit,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
        content = content,
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    )
}

@Composable
private fun ItemRow(
    content: LazyListScope.() -> Unit,
    modifier: Modifier,
) {
    LazyRow(
        modifier = modifier,
        content = content,
        contentPadding = padding,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    )
}