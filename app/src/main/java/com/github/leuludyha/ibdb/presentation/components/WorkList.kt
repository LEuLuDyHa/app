package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.github.leuludyha.domain.model.CoverSize
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.presentation.navigation.Screen

enum class Orientation { Vertical, Horizontal }

@Composable
fun WorkList(
    orientation: Orientation,
    works: LazyPagingItems<Work>,
    navController: NavHostController,
) {
    val content: LazyListScope.() -> Unit = {
        items(items = works, key = { it.id }) { work ->
            work?.let {
                WorkListItem(
                    work = work,
                    navController = navController,
                )
            }
        }
    }

    if (orientation == Orientation.Vertical) {
        WorksColumn(content)
    } else if (orientation == Orientation.Horizontal) {
        WorksRow(content)
    }
}

private val padding: PaddingValues = PaddingValues(
    horizontal = 8.dp,
    vertical = 4.dp,
)

private val itemHeight: Dp = 100.dp

@Composable
private fun WorksColumn(content: LazyListScope.() -> Unit) {
    LazyColumn(
        content = content,
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    )
}

@Composable
private fun WorksRow(content: LazyListScope.() -> Unit) {
    LazyRow(
        content = content,
        contentPadding = padding,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun WorkListItem(
    work: Work,
    navController: NavHostController,
) {
    val covers = work.covers.collectAsStateWithLifecycle(initialValue = emptyList())
    val authors = work.authors.collectAsStateWithLifecycle(initialValue = emptyList())
    val subjects = work.subjects.collectAsStateWithLifecycle(initialValue = emptyList())

    ElevatedCard(
        modifier = Modifier
            .padding()
            .height(itemHeight)
            .fillMaxWidth(),
        // On click, navigate to the book's details screen using its id
        onClick = {
            navController.navigate(
                route = Screen.BookDetails.passBookId(work.id)
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(route = Screen.BookDetails.passBookId(work.id))
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (covers.value.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(
                        data = covers.value[0].urlForSize(CoverSize.Large),
                        builder = {
                            crossfade(true)
                            scale(Scale.FILL)
                        }),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Book Cover"
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 5.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
            ) {
                // Display the title of the book
                work.title?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                // Display the name of the authors
                Text(
                    text = authors.value.toText(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.testTag("worklist::author_name")
                )
                Spacer(modifier = Modifier.height(3.dp))
                // Display the list of subjects of the book
                SubjectList(subjectNames = subjects.value)
                // Other stuff if needed
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SubjectList(
    subjectNames: List<String>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        subjectNames.map {
            SuggestionChip(
                onClick = { /*TODO*/ },
                label = { Text(text = it, style = MaterialTheme.typography.labelSmall) }
            )
        }
    }
}