package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkListTest {

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun oneWorkHasAllItsInfoDisplayed() {
        // To avoid infinite loop
        val dumbWork = Work (
            id = "My Little Rotweiler ate a Lamb",
            title ="cool-id",
            editions = flowOf(listOf()),
            authors = flowOf(listOf()),
            covers = flowOf(listOf(Cover(-1))),
            subjects = flowOf(listOf("Dog", "Murder Mystery"))
        )

        val testAuthor = Author(
            id = "cool-id",
            name ="John Mockentosh",
            birthDate = "01.01.01",
            deathDate = "02.02.02",
            wikipedia = "wikipedia.test",
            works = flowOf(listOf(dumbWork)),
            photos = flowOf(listOf())
        )

        val testEdition = Edition(
            id = "editionId",
            title = "editionTitle",
            isbn13 = "isbn13",
            isbn10 = null,
            authors = flowOf(listOf(testAuthor)),
            works = flowOf(listOf()),
            covers = flowOf(listOf())
            )

        val testWork = Work(
            id = "My Little Rotweiler ate a Lamb",
            title ="cool-id",
            editions = flowOf(listOf(testEdition)),
            authors = flowOf(listOf(testAuthor)),
            covers = flowOf(listOf(Cover(-1))),
            subjects = flowOf(listOf("Dog", "Murder Mystery"))
        )

        // TODO how to test lazyPagingItems?
        /*composeTestRule.setContent {
            WorkList(
                orientation = Orientation.Vertical,
                works = listOf(testWork),
                navController = navController,
                paddingValues = PaddingValues(0.dp)
            )
        }

        composeTestRule.waitForIdle()

        testWork.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }

        testWork.subjects.forEach {
            composeTestRule.onNodeWithText(it, substring = false).assertExists()
        }*/

    }
}