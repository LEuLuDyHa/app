package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.useCase.SearchRemotelyUseCase
import com.github.leuludyha.ibdb.presentation.components.search.BookSearchViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookSearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bookSearchScreenDoesNotCrash() {
        composeTestRule.setContent {
            BookSearchScreen(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                padding = PaddingValues(4.dp),
                bookSearchViewModel = BookSearchViewModel(SearchRemotelyUseCase(MockLibraryRepositoryImpl()))
            )
        }

        assert(true)
    }
}