package com.github.leuludyha.ibdb.presentation.screen.book_details

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
import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.useCase.DeleteWorkPrefLocallyUseCase
import com.github.leuludyha.domain.useCase.GetAllWorkPrefsLocallyUseCase
import com.github.leuludyha.domain.useCase.GetWorkRemotelyUseCase
import com.github.leuludyha.domain.useCase.SaveWorkPrefLocallyUseCase
import com.github.leuludyha.ibdb.EmptyDataMockLibraryRepositoryImpl
import com.github.leuludyha.ibdb.LoadingMockLibraryRepositoryImpl
import com.github.leuludyha.ibdb.MockTrueNetworkProvider
import com.github.leuludyha.ibdb.NullErrorMessageMockLibraryRepositoryImpl
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControlViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun workNameIsDisplayedOnSuccess() {
        composeTestRule.setContent {
            BookDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                workId = workMrFox.id,
                viewModel = BookDetailsScreenViewModel(Mocks.authContext, GetWorkRemotelyUseCase(MockLibraryRepositoryImpl(), MockTrueNetworkProvider)),
                readingStateControlViewModel = ReadingStateControlViewModel(
                    GetAllWorkPrefsLocallyUseCase(MockLibraryRepositoryImpl()),
                    SaveWorkPrefLocallyUseCase(MockLibraryRepositoryImpl()),
                    DeleteWorkPrefLocallyUseCase(MockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText(workMrFox.title!!).assertExists()
    }

    @Test
    fun errorIsDisplayedOnSuccessEmptyData() {
        composeTestRule.setContent {
            BookDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                workId = workMrFox.id,
                viewModel = BookDetailsScreenViewModel(Mocks.authContext, GetWorkRemotelyUseCase(EmptyDataMockLibraryRepositoryImpl(), MockTrueNetworkProvider)),
                readingStateControlViewModel = ReadingStateControlViewModel(
                    GetAllWorkPrefsLocallyUseCase(EmptyDataMockLibraryRepositoryImpl()),
                    SaveWorkPrefLocallyUseCase(EmptyDataMockLibraryRepositoryImpl()),
                    DeleteWorkPrefLocallyUseCase(EmptyDataMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun errorIsDisplayedOnErrorNonNullMessage() {
        composeTestRule.setContent {
            BookDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                workId = work1984.id,
                viewModel = BookDetailsScreenViewModel(Mocks.authContext, GetWorkRemotelyUseCase(MockLibraryRepositoryImpl(), MockTrueNetworkProvider)),
                readingStateControlViewModel = ReadingStateControlViewModel(
                    GetAllWorkPrefsLocallyUseCase(MockLibraryRepositoryImpl()),
                    SaveWorkPrefLocallyUseCase(MockLibraryRepositoryImpl()),
                    DeleteWorkPrefLocallyUseCase(MockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("id not found").assertExists()
    }
    @Test
    fun notFoundIsDisplayedOnErrorNullMessage() {
        composeTestRule.setContent {
            BookDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                workId = workMrFox.id,
                viewModel = BookDetailsScreenViewModel(Mocks.authContext, GetWorkRemotelyUseCase(NullErrorMessageMockLibraryRepositoryImpl(), MockTrueNetworkProvider)),
                readingStateControlViewModel = ReadingStateControlViewModel(
                    GetAllWorkPrefsLocallyUseCase(NullErrorMessageMockLibraryRepositoryImpl()),
                    SaveWorkPrefLocallyUseCase(NullErrorMessageMockLibraryRepositoryImpl()),
                    DeleteWorkPrefLocallyUseCase(NullErrorMessageMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithText("Not Found").assertExists()
    }

    @Test
    fun loadingProgressIndicatorIsDisplayedWhenLoading() {
        composeTestRule.setContent {
            BookDetailsScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                workId = workMrFox.id,
                viewModel = BookDetailsScreenViewModel(Mocks.authContext, GetWorkRemotelyUseCase(LoadingMockLibraryRepositoryImpl(), MockTrueNetworkProvider)),
                readingStateControlViewModel = ReadingStateControlViewModel(
                    GetAllWorkPrefsLocallyUseCase(LoadingMockLibraryRepositoryImpl()),
                    SaveWorkPrefLocallyUseCase(LoadingMockLibraryRepositoryImpl()),
                    DeleteWorkPrefLocallyUseCase(LoadingMockLibraryRepositoryImpl())
                )
            )
        }

        composeTestRule.onNodeWithTag("progressIndicator").assertExists()
    }
}