package com.github.leuludyha.ibdb.presentation.components.reading_list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.DeleteWorkPrefLocallyUseCase
import com.github.leuludyha.domain.useCase.GetAllWorkPrefsLocallyUseCase
import com.github.leuludyha.domain.useCase.GetWorkPrefLocallyUseCase
import com.github.leuludyha.domain.useCase.SaveWorkPrefLocallyUseCase
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControl
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControlViewModel
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.TestTags
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import onNodeByTag
import org.hamcrest.MatcherAssert.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class ReadingStateControlTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val work: Work = Mocks.work1984
    private lateinit var libraryRepository: LibraryRepository

    @Before
    fun initContent() {
        val workPref = WorkPreference(work, WorkPreference.ReadingState.READING, true)

        libraryRepository = MockLibraryRepositoryImpl()
        runBlocking {
            libraryRepository.saveLocally(work)
            libraryRepository.saveLocally(workPref)
        }

        composeTestRule.setContent {
            ReadingStateControl(
                work = work,
                viewModel = ReadingStateControlViewModel(
                    GetAllWorkPrefsLocallyUseCase(libraryRepository = libraryRepository),
                    SaveWorkPrefLocallyUseCase(libraryRepository = libraryRepository),
                    DeleteWorkPrefLocallyUseCase(libraryRepository = libraryRepository)
                )
            )
        }
    }

    @Test
    fun displaysAlreadyExistingState() {
        // Maybe the mocked object can change so just handle this case
        composeTestRule.onNodeWithText(
            WorkPreference.ReadingState.READING.toString(), substring = true
        ).assertExists("Reading state label does not exist in hierarchy !")
    }

    @Test
    fun dislikeMakesReadingStateControllerDisappear() {
        val likeButton = composeTestRule.onNodeByTag(TestTags.likeButton)
        val readingStateController = composeTestRule.onNodeByTag(TestTags.readingStateController)

        readingStateController.assertExists("Work should already be liked")
        likeButton.performClick()
        readingStateController.assertDoesNotExist()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun dislikeAndLikeModifiesEntryToPreferences() {
        val likeButton = composeTestRule.onNodeByTag(TestTags.likeButton)

        // Dislike, remove entry
        runTest{
            likeButton.performClick()
            assertThat(
                "Work is no longer in preferences",
                GetWorkPrefLocallyUseCase(libraryRepository)(work.id).firstOrNull() == null
            )
            likeButton.performClick()
            assertThat(
                "Work is back in preferences",
                GetWorkPrefLocallyUseCase(libraryRepository)(work.id).firstOrNull() != null
            )
        }
    }

    @Test
    fun changingStateChangesPreferencesObject() {
        val stateController = composeTestRule.onNodeByTag(TestTags.readingStateController)

        // Open state menu
        stateController.performClick()
        val finishedButton = composeTestRule.onNodeWithText(
            WorkPreference.ReadingState.FINISHED.toString()
        )
        finishedButton.assertExists("Menu should be opened and propose \"Finished\" state")

        finishedButton.performClick()
        composeTestRule.onNodeWithText(
            WorkPreference.ReadingState.FINISHED.toString()
        ).assertExists("State should be FINISHED")
    }

}