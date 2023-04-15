package com.github.leuludyha.ibdb.presentation.components.reading_list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference
import com.github.leuludyha.ibdb.presentation.components.reading_list.controls.ReadingStateControl
import com.github.leuludyha.ibdb.presentation.components.reading_list.controls.ReadingStateControlViewModel
import com.github.leuludyha.ibdb.presentation.components.reading_list.controls.TestTags
import onNodeByTag
import org.hamcrest.MatcherAssert.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReadingStateControlTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val work: Work = Mocks.work1984
    private lateinit var preferences: UserPreferences
    private lateinit var state: WorkPreference

    @Before
    fun initContent() {
        preferences = UserPreferences()
        preferences.addPreference(
            WorkPreference(work, WorkPreference.ReadingState.READING, true)
        )

        state = preferences.workPreferences[work.id]!!

        composeTestRule.setContent {
            ReadingStateControl(
                work = work,
                viewModel = ReadingStateControlViewModel(
                    AuthenticationContext(
                        User(
                            username = "Bobby",
                            profilePictureUrl = null,
                            preferences = preferences
                        )
                    )
                )
            )
        }
    }

    @Test
    fun displaysAlreadyExistingState() {
        // Maybe the mocked object can change so just handle this case
        composeTestRule.onNodeWithText(
            state.readingState.toString(), substring = true
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

    @Test
    fun likeAndDislikeModifiesEntryToPreferences() {
        val likeButton = composeTestRule.onNodeByTag(TestTags.likeButton)

        // Dislike, remove entry
        likeButton.performClick()
        assertThat(
            "Work is no longer in preferences",
            !preferences.workPreferences.containsKey(work.id)
        )
        likeButton.performClick()
        assertThat(
            "Work is back in preferences",
            preferences.workPreferences.containsKey(work.id)
        )
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
        assertThat(
            "State has changed",
            state.readingState == WorkPreference.ReadingState.FINISHED
        )
    }

}