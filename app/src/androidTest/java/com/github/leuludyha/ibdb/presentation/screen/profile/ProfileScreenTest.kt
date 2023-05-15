package com.github.leuludyha.ibdb.presentation.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun usernameIsDisplayed() {
        composeTestRule.setContent {
            ProfileScreen(
                padding = PaddingValues(4.dp),
                viewModel = ProfileScreenViewModel(Mocks.authContext)
            )
        }

        composeTestRule.onNodeWithText(Mocks.authContext.principal.username).assertExists()
    }
}