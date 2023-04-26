package com.github.leuludyha.ibdb.presentation.components.books.book_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControl
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

@Composable
fun FullBookView(
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
        Text(text = work.title.orEmpty(), style = MaterialTheme.typography.titleLarge)
        ReadingStateControl(work = work)
        MiniAuthorViews(authors.value, navController)
        Image(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            painter = rememberImagePainter(
                data = covers.value.firstOrNull()?.urlForSize(CoverSize.Large),
                builder = {
                    crossfade(true)
                    scale(Scale.FILL)
                }),
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
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    IBDBTheme {
        FullBookView(
            navController = rememberNavController(),
            work = Mocks.work1984
        )
    }
}
