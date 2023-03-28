package com.github.leuludyha.ibdb.presentation.components.reading_list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReadingListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val work1: Work = Mocks.work1984
    private val work2: Work = Mocks.workLaFermeDesAnimaux
    private lateinit var preferences: UserPreferences
    private lateinit var state: WorkPreference

    private lateinit var navController: NavHostController

    @Before
    fun initContent() {
        preferences = UserPreferences()
        preferences.addPreference(
            WorkPreference(work1, WorkPreference.ReadingState.READING, true)
        )

        state = preferences.preferencesByWorkId[work1.getId()]!!

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            ReadingList(
                navController = navController,
                preferences = preferences,
            )
        }
    }

    @Test
    fun likedWorkIsInList() {
        composeTestRule.onNodeWithText(work1.title!!)
            .assertExists("A liked work should appear in the reading list")
    }

    @Test
    fun notLikedWorkIsNotInList() {
        composeTestRule.onNodeWithText(work2.title!!)
            .assertDoesNotExist()
    }

}