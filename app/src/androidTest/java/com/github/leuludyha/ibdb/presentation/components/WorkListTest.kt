package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.Orientation
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
            ItemList(
                orientation = Orientation.Vertical,
                values = listOf(testWork),
            ) { assert(it.title == testWork.title) }
        }

        composeTestRule.waitForIdle()
    }
}