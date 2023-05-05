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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.books.WorkList
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.ReadingList
import com.github.leuludyha.ibdb.presentation.components.books.recommendations.RecommendationList
import com.github.leuludyha.ibdb.presentation.components.utils.WithHeader
import com.github.leuludyha.ibdb.ui.theme.IBDBTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: HomeScreenViewModel = hiltViewModel(),
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
    ) {
        WithHeader(header = stringResource(id = R.string.reading_list_title)) {
            ReadingList(
                navController = navController,
                workPreferences = workPreferences.value
            )
        }
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
        RecommendationList(navController, onRecommendations = { setEmpty(it) })
        Divider()
        WithHeader(header = stringResource(id = R.string.weekly_popular_label)) {
            WorkList(navController = navController, works = Mocks.weeklyPopularWorks)
        }
        Divider()
        WithHeader(header = stringResource(id = R.string.more_works_by) + Mocks.authorJrrTolkien.name) {
            WorkList(navController = navController, works = Mocks.jrrTolkienWorks)
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    IBDBTheme {
        HomeScreen(
            navController = rememberNavController(),
            outerPadding = PaddingValues(0.dp)
        )
    }
}