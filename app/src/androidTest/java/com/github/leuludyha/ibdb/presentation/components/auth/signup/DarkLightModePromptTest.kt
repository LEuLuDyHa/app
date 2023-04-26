package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
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
class DarkLightModePromptTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun darkThemeIsChangedToLightWhenButtonClickedOnDarkTheme() {
        val user = Mocks.mainUser
        val authContext = Mocks.authContext

        DarkLightModePrompt.isDynamicCompatible = false

        user.userPreferences.darkTheme.component2()(true)

        // Mount the prompt on the view
        composeTestRule.setContent {
            DarkLightModePrompt.Display(authContext = authContext) {
                assertThat(user.userPreferences.darkTheme.value, Is(false))
            }
        }

        composeTestRule.onNodeByTag(DarkLightModePrompt.TestTags.toggleThemeBtn)
            .performClick()

        // Trigger onComplete() function
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .performClick()
    }

    @Test
    fun textIsAsExpectedInToggleButton() {
        val authContext = Mocks.authContext

        // Go though all branches
        DarkLightModePrompt.isDynamicCompatible = false

        // Mount the prompt on the view
        composeTestRule.setContent {
            DarkLightModePrompt.Display(authContext = authContext) { }
        }
        // This is just to increase coverage :/
        composeTestRule.onNodeWithText("Toggle Dark/Light Theme")
            .assertExists()
    }

    @Test
    fun textIsAsExpectedInNextButton() {
        val authContext = Mocks.authContext

        // Go though all branches
        DarkLightModePrompt.isDynamicCompatible = false

        // Mount the prompt on the view
        composeTestRule.setContent {
            DarkLightModePrompt.Display(authContext = authContext) { }
        }
        // This is just to increase coverage :/
        composeTestRule.onNodeWithText("Next")
            .assertExists()
    }

    @Test
    fun lightThemeIsChangedToDarkWhenButtonClickedOnLightTheme() {
        val user = Mocks.mainUser
        val authContext = Mocks.authContext
        // This makes the CLI crash
        DarkLightModePrompt.isDynamicCompatible = false

        user.userPreferences.darkTheme.component2()(false)

        // Mount the prompt on the view
        composeTestRule.setContent {
            DarkLightModePrompt.Display(authContext = authContext) {
                assertThat(user.userPreferences.darkTheme.value, Is(true))
            }
        }

        composeTestRule.onNodeByTag(DarkLightModePrompt.TestTags.toggleThemeBtn)
            .performClick()

        // Trigger onComplete() function
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .performClick()
    }

}