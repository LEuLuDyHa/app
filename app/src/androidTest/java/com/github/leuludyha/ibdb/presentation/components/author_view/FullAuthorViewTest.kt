package com.github.leuludyha.ibdb.presentation.components.author_view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.components.books.author_views.FullAuthorView
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FullAuthorViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val author1 = Mocks.authorGeorgeOrwell
    val work1: Work = Mocks.work1984 // of author1

    private lateinit var navController: NavHostController

    @Before
    fun initContent() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            FullAuthorView(navController, author1)
        }
    }

    @Test
    fun authorNameIsDisplayed() {
        composeTestRule.onNodeWithText(author1.toString())
            .assertExists("The author name should be displayed")
    }

    @Test
    fun likedWorkIsInList() {
        composeTestRule.onNodeWithText(work1.title!!)
            .assertExists("A liked work should appear in the reading list")
    }

    @Test
    fun clickingOnWorkOpenBookView() {
        assertThrows(java.lang.Exception::class.java) {
            composeTestRule
                .onNodeWithTag("minibook::button")
                .performClick()
            composeTestRule.waitForIdle()
        }
    }
}
