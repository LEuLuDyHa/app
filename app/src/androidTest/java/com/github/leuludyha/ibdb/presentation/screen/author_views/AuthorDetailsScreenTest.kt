package com.github.leuludyha.ibdb.presentation.screen.author_views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.useCase.GetAuthorRemotelyUseCase
import com.github.leuludyha.ibdb.EmptyDataMockLibraryRepositoryImpl
import com.github.leuludyha.ibdb.LoadingMockLibraryRepositoryImpl
import com.github.leuludyha.ibdb.NullErrorMessageMockLibraryRepositoryImpl
import com.google.common.truth.Truth.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthorDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun authorNameIsDisplayedOnSuccess() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorRoaldDahl.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(MockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText(authorRoaldDahl.name!!).assertExists()
    }

    @Test
    fun errorIsDisplayedOnSuccessEmptyData() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorRoaldDahl.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(EmptyDataMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun errorIsDisplayedOnErrorNonNullMessage() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorGeorgeOrwell.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(MockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("id not found").assertExists()
    }

    @Test
    fun notFoundIsDisplayedOnErrorNullMessage() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorGeorgeOrwell.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(NullErrorMessageMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("Not Found").assertExists()
    }

    @Test
    fun loadingProgressIndicatorIsDisplayedWhenLoading() {
        composeTestRule.setContent {
            AuthorDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                authorId = authorGeorgeOrwell.id,
                viewModel = AuthorDetailsScreenViewModel(
                    _authContext = Mocks.authContext,
                    authorById = GetAuthorRemotelyUseCase(LoadingMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithTag("progressIndicator").assertExists()
    }
}