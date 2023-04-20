package com.github.leuludyha.ibdb.presentation.components.books.book_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.ReadingList
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
                // Each item of the reading list will be a mini book view
                /*MiniAuthorView( TODO
                    author = author,
                    // On click, redirect to the BookDetails screen on this book
                    onClick = { clickedAuthor ->
                        navController.navigate(Screen.AuthorDetails.passAuthorId(clickedAuthor.Id()))
                    }
                )*/
            }
        }
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
