package com.github.leuludyha.ibdb.presentation.components.books.recommendations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.presentation.components.ItemList
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.github.leuludyha.ibdb.presentation.navigation.Screen

/**
 * A list of recommendations for the user
 * TODO Make it infinite and lazy
 * @param onRecommendations Called each time recommendations are updated, the boolean
 * parameter indicates whether the list of recommendations is empty or not
 */
@Composable
fun RecommendationList(
    navController: NavHostController,
    viewModel: RecommendationListViewModel = hiltViewModel(),
    onRecommendations: (Boolean) -> Unit
) {
    val recommendations = viewModel.getRecommendations().collectAsState(initial = listOf())

    LaunchedEffect(recommendations) {
        onRecommendations(recommendations.value.isEmpty())
    }

    ItemList(
        values = recommendations.value,
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
