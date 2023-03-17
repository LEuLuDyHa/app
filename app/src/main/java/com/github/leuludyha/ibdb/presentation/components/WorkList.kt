package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.github.leuludyha.domain.model.Book
import com.github.leuludyha.ibdb.presentation.navigation.Screen

object BookList {

    enum class Orientation { Vertical, Horizontal }

    @Composable
    fun Component(
        orientation: Orientation,
        books: LazyPagingItems<Book>,
        navController: NavHostController
    ) {
        val content: LazyListScope.() -> Unit = {
            items(
                items = books,
                key = { book ->
                    book.id
                }
            ) { book ->
                if (book != null) { BookListItem(book = book, navController = navController) }
            }
        }

        if (orientation == Orientation.Vertical) { BookColumn(content) }
        else if (orientation == Orientation.Horizontal) { BookRow(content) }
    }
}

private val padding: PaddingValues = PaddingValues(
    horizontal = 8.dp,
    vertical = 4.dp,
)

private val itemHeight: Dp = 180.dp

@Composable
private fun BookColumn(content: LazyListScope.() -> Unit) {
    LazyColumn(content = content, contentPadding = padding)
}

@Composable
private fun BookRow(content: LazyListScope.() -> Unit) {
    LazyRow(content = content, contentPadding = padding)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BookListItem(
    book: Book,
    navController: NavHostController
) {
    OutlinedCard(
        modifier = Modifier
            .padding()
            .height(itemHeight)
            .fillMaxWidth(),
        // On click, navigate to the book's details screen using its id
        onClick = { navController.navigate(
            route = Screen.BookDetails.passBookId(book.id)
        ) }
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            // Display the book's thumbnail
            book.covers?.let { book.covers[0] }
            // Display the book's summary info
            Column(
                modifier = Modifier
                    .padding(start = 2.dp, end = 5.dp, bottom = 4.dp, top = 4.dp)
                    .height(IntrinsicSize.Max)
            ) {
                book.title?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
                Spacer(modifier = Modifier.height(1.dp))
                book.authors?.let { Text(text = it.name, style = MaterialTheme.typography.titleSmall) }
                Spacer(modifier = Modifier.height(3.dp))
                book.subjects?.let { SubjectList(it) }
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
        modifier = Modifier.width(IntrinsicSize.Max)
    ) {subjectNames.map {
        SuggestionChip(
            onClick = { /*TODO*/ },
            label = { Text(text = it, style = MaterialTheme.typography.labelSmall)}
        )
    } }
}