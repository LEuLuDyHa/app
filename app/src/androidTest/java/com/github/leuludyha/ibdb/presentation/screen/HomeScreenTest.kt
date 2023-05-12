package com.github.leuludyha.ibdb.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.library.MockUserRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.recommendation.RecommenderSystem
import com.github.leuludyha.ibdb.presentation.components.books.recommendations.RecommendationListViewModel
import com.google.common.truth.Truth.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun readingListLabelIsPresent() {
        composeTestRule.setContent {
            HomeScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                outerPadding = PaddingValues(4.dp),
                viewModel = HomeScreenViewModel(Mocks.authContext),
                RecommendationListViewModel(
                    _recommender = RecommenderSystem(MockUserRepositoryImpl()),
                    _authContext = Mocks.authContext
                )
            )
        }

        composeTestRule.onNodeWithText("Reading List").assertExists()
    }

    @Test
    fun workInReadingListIsPresent() {
        composeTestRule.setContent {
            HomeScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                outerPadding = PaddingValues(4.dp),
                viewModel = HomeScreenViewModel(Mocks.authContext),
                RecommendationListViewModel(
                    _recommender = RecommenderSystem(MockUserRepositoryImpl()),
                    _authContext = Mocks.authContext
                )
            )
        }

        composeTestRule.onNodeWithText(work1984.title!!).assertExists()
    }

    @Test
    fun selectedForYouLabelNotPresentWhenNoRecommendations() {
        composeTestRule.setContent {
            HomeScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                outerPadding = PaddingValues(4.dp),
                viewModel = HomeScreenViewModel(Mocks.authContext),
                RecommendationListViewModel(
                    _recommender = RecommenderSystem(MockUserRepositoryImpl()),
                    _authContext = Mocks.authContext
                )
            )
        }

        composeTestRule.onNodeWithText("Selected for you…").assertDoesNotExist()
    }
}