package com.github.leuludyha.ibdb.presentation.components.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.navigation.Screen

@Composable
fun WorkList(
    navController: NavHostController,
    works: List<Work>,
) {
    ItemList(
        // TODO Maybe change this to be sorted using user rating/ other factors
        values = works.toList(),
        modifier = Modifier.wrapContentSize()
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
                })

        }
    }
}