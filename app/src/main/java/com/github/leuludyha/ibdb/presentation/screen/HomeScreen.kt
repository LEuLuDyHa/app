package com.github.leuludyha.ibdb.presentation.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.ReadingList
import com.github.leuludyha.ibdb.presentation.components.books.recommendations.RecommendationList
import com.github.leuludyha.ibdb.presentation.components.books.recommendations.RecommendationListViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    recommendationListViewModel: RecommendationListViewModel = hiltViewModel(),
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    val (isEmpty, setEmpty) = remember { mutableStateOf(true) }

    SideEffect {
        systemUiController.setStatusBarColor(color = systemBarColor)
    }

    val workPreferences = viewModel.authContext.principal.workPreferences.collectAsState(initial = mapOf())

    Column(
        modifier = Modifier
            .padding(outerPadding)
            .fillMaxWidth()
            .verticalScroll(state = ScrollState(0), enabled = true)
            .testTag("Home::main")
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            text = stringResource(id = R.string.reading_list_title),
            style = MaterialTheme.typography.headlineMedium
        )
        ReadingList(
            navController = navController,
            workPreferences = workPreferences.value
        )
        Divider()
        // Only display the header if the recommendation list is not empty
        if (!isEmpty) {
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = stringResource(id = R.string.recommendation_list_title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        // The recommendation list is not visible if empty either way
        RecommendationList(
            navController = navController,
            viewModel = recommendationListViewModel,
            onRecommendations = { setEmpty(it) })
    }
}