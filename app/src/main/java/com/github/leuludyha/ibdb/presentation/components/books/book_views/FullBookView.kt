package com.github.leuludyha.ibdb.presentation.components.books.book_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControl
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControlViewModel
import com.github.leuludyha.ibdb.presentation.components.utils.ItemList
import com.github.leuludyha.ibdb.presentation.navigation.Screen

@Composable
fun FullBookView(
    readingStateControlViewModel: ReadingStateControlViewModel = hiltViewModel(),
    navController: NavHostController,
    work: Work
) {
    val covers = work.covers.collectAsState(initial = listOf())
    val authors = work.authors.collectAsState(initial = listOf())
    val subjects = work.subjects.collectAsState(initial = listOf())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = work.title.orEmpty(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ReadingStateControl(work = work, readingStateControlViewModel)
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { navController.navigate(Screen.Share.shareBookId(work.id)) }
            ) { Icon(Icons.Filled.Send, "Share") }
        }
        MiniAuthorViews(authors.value, navController)
        Image(
            modifier = Modifier
                .testTag("full_book_view::book_cover")
                .height(300.dp)
                .fillMaxWidth(),
            painter = rememberImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = covers.value.firstOrNull()?.urlForSize(CoverSize.Large))
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        scale(Scale.FILL)
                    }).build()
            ),
            contentScale = ContentScale.FillWidth,
            contentDescription = stringResource(id = R.string.ui_bookCover_altText)
        )
        Subjects(subjects.value)
    }
}

@Composable
fun MiniAuthorViews(
    authors: List<Author>,
    navController: NavHostController
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val authorLabel = stringResource(id = R.string.full_book_view_author_label)
        Text(
            text = when (authors.size) {
                0 -> ""
                1 -> "$authorLabel: "
                else -> "${authorLabel}s: "
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        ItemList(
            values = authors,
            modifier = Modifier.wrapContentSize()
        ) { author ->
            Column(modifier = Modifier.wrapContentHeight()) {
                Button(
                    onClick = {
                        navController.navigate(Screen.AuthorDetails.passAuthorId(author.Id()))
                    }
                ) {
                    Text(
                        modifier = Modifier.testTag("mini_author_view::author_name"),
                        text = author.name.orEmpty(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Composable
fun Subjects(
    subjects: List<String>,
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val subjectLabel = stringResource(id = R.string.full_book_view_subject_label)
        Text(
            text = when (subjects.size) {
                0 -> ""
                1 -> "$subjectLabel: "
                else -> "${subjectLabel}s: "
            },
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = subjects.take(3).toText(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
