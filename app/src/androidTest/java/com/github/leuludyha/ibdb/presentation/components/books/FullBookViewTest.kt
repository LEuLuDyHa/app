package com.github.leuludyha.ibdb.presentation.components.books

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.ibdb.presentation.components.books.author_views.FullAuthorView
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FullBookViewTest {

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

    fun titleIsDisplayed() {

    }
}