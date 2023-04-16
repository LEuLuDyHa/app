package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import onNodeByTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpPromptBaseTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun nextButtonIsNotDisplayedForRequiredPrompts() {
        val prompt = object : SignUpPromptBase(
            required = true
        ) {
            @Composable
            override fun Content(authContext: AuthenticationContext, onComplete: () -> Unit) {
                Text(text = "")
            }

            @Composable
            override fun Title(authContext: AuthenticationContext) {
                Text(text = "")
            }
        }

        composeTestRule.setContent { prompt.Display(authContext = Mocks.authContext) { } }
        // Should not exist if optional
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .assertDoesNotExist()
    }

    @Test
    fun nextButtonIsDisplayedForOptionalPrompts() {
        val prompt = object : SignUpPromptBase(
            required = false
        ) {
            @Composable
            override fun Content(authContext: AuthenticationContext, onComplete: () -> Unit) {
                Text(text = "")
            }

            @Composable
            override fun Title(authContext: AuthenticationContext) {
                Text(text = "")
            }
        }

        composeTestRule.setContent { prompt.Display(authContext = Mocks.authContext) { } }
        // Should not exist if optional
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .assertExists()
    }

    @Test
    fun defaultTitleDisplaysTextPassedAsArgument() {
        val titleText = "Title"

        val prompt = object : SignUpPromptBase(
            required = true
        ) {
            @Composable
            override fun Content(authContext: AuthenticationContext, onComplete: () -> Unit) {
                Text(text = "")
            }

            @Composable
            override fun Title(authContext: AuthenticationContext) {
                DefaultTitle(text = titleText)
            }
        }

        composeTestRule.setContent { prompt.Display(authContext = Mocks.authContext) { } }

        composeTestRule.onNodeWithText(titleText).assertExists()
    }
}