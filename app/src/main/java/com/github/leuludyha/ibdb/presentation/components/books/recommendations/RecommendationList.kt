package com.github.leuludyha.ibdb.presentation.components.books.recommendations

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.navigation.Screen

/**
 * A list of recommendations for the user
 * TODO Make it infinite and lazy
 */
@Composable
fun RecommendationList(
    navController: NavHostController,
    viewModel: RecommendationListViewModel = hiltViewModel()
) {
    ItemList(
        values = viewModel.getRecommendations(),
    ) {
        MiniBookView(
            work = it,
            displaySubjects = false,
            onClick = { clickedWork ->
                navController.navigate(Screen.BookDetails.passBookId(clickedWork.id))
            }
        )
    }
}
