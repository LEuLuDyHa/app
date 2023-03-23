package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.CoverSize
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.domain.model.formatListToText
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.navigation.Screen

enum class Orientation { Vertical, Horizontal }

@Composable
fun WorkList(
    orientation: Orientation,
    works: List<Work>,
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: WorkListViewModel = hiltViewModel()
) {
    val content: LazyListScope.() -> Unit = {
        items(items = works, key = { it.id }) { work ->
            WorkListItem(
                work = work,
                navController = navController,
                viewModel = viewModel
            )
        }
    }

    Surface(modifier = Modifier.padding(paddingValues)) {
        if (orientation == Orientation.Vertical) {
            WorksColumn(content)
        } else if (orientation == Orientation.Horizontal) {
            WorksRow(content)
        }
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
    viewModel: WorkListViewModel
) {
    val (authors, setAuthors) = remember { mutableStateOf<List<Author>?>(null); }

    SideEffect {
        viewModel.getAuthorsOf(work) { setAuthors(it) }
    }

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
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            // Display the book's thumbnail
            work.coverUrls?.let {
                Image(
                    painter = rememberImagePainter(
                        data = it[0].invoke(CoverSize.Large),
                        builder = {
                            crossfade(true)
                            scale(Scale.FILL)
                        }),
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(id = R.string.ui_bookCover_altText)
                )
            }
            // Display the book's summary info
            Column(
                modifier = Modifier
                    .padding(start = 5.dp, end = 8.dp, bottom = 4.dp, top = 4.dp)
                    .height(IntrinsicSize.Max)
            ) {
                // Display the title of the book
                work.title?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                // Display the name of the authors
                authors?.let {
                    Text(
                        text = formatListToText(it),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.testTag("worklist::author_name")
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                // Display the list of subjects of the book
                SubjectList(subjectNames = work.subjects)
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
        modifier = Modifier.width(IntrinsicSize.Max),
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