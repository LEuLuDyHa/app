package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Work
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
        val testWork = Work(
            "My Little Rotweiler ate a Lamb",
            "cool-id",
            fetchAuthors = suspend {
                listOf(
                    Author(
                        "", "John Mockentosh", "cool-id",
                        null, null, null
                    )
                )
            },
            coverUrls = listOf { "" },
            subjects = listOf("Dog", "Murder Mystery")
        )

        composeTestRule.setContent {
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
        }

    }
}