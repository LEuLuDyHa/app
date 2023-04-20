package com.github.leuludyha.ibdb.presentation.components.books.author_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.github.leuludyha.ibdb.presentation.components.ItemList
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
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.book_views.FullBookView
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookListView
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.ReadingList
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControl
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme

@Composable
fun FullAuthorView(
    navController: NavHostController,
    author: Author
) {

    val works = author.works.collectAsState(initial = listOf())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = author.toString(), style = MaterialTheme.typography.titleLarge)

        ItemList(
            values = works.value,
            modifier = Modifier.wrapContentSize(),
        ) { work ->
            Column(modifier = Modifier.wrapContentHeight()) {
                // Each item of the reading list will be a mini book view
                MiniBookView(
                    work = work,
                    // Do not display the work's subjects, too much info is not needed
                    displaySubjects = false,
                    // On click, redirect to the BookDetails screen on this book
                    onClick = { clickedWork ->
                        navController.navigate(Screen.BookDetails.passBookId(clickedWork.Id()))
                    }
                )
            }
        }

    }

}

@Preview
@Composable
fun DefaultPreview() {
    IBDBTheme {
        FullAuthorView(
            navController = rememberNavController(),
            author = Mocks.authorGeorgeOrwell
        )
    }
}
