package com.github.leuludyha.ibdb.presentation.components.books.author_views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.components.utils.ItemList
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
