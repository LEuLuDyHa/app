package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks
import onNodeByTag
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.`is` as Is

@RunWith(AndroidJUnit4::class)
class SignUpWalkThroughTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pressingNextButtonDisplaysNext() {
        composeTestRule.setContent {
            SignUpWalkThrough(
                authContext = Mocks.authContext,
                prompts = listOf(
                    ChangeUsernamePrompt,
                    DarkLightModePrompt,
                )
            ) { /* Do Nothing */ }
        }

        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .performClick()

        // If we pressed on next for username, toggle theme button should be displayed
        composeTestRule.onNodeByTag(DarkLightModePrompt.TestTags.toggleThemeBtn)
            .assertExists()
    }

    @Test
    fun onCompleteIsInvokedWhenAllPromptsAreCompleted() {
        var passedPrompts = 0
        val prompts = listOf(
            ChangeUsernamePrompt,
            ChangeProfilePicturePrompt,
            DarkLightModePrompt,
        )

        composeTestRule.setContent {
            SignUpWalkThrough(
                authContext = Mocks.authContext,
                prompts = prompts
                // On Complete is invoked when we passed all the prompts
            ) { assertThat(passedPrompts, Is(prompts.size)) }
        }
        passedPrompts++
        // Skip profile picture
        passedPrompts++

        // Skip username
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .performClick()
        passedPrompts++
        // Skip theme
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .performClick()
    }

}