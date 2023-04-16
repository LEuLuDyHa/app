package com.github.leuludyha.ibdb.presentation.components.auth.signup

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
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
class ChangeUsernamePromptTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun usernameIsChangedInUserObject() {
        val user = Mocks.mainUser
        val authContext = Mocks.authContext

        val newUsername = "Username"

        // Mount the prompt on the view
        composeTestRule.setContent {
            ChangeUsernamePrompt.Display(authContext = authContext) {
                assertThat(user.username, Is(newUsername))
            }
        }
        // Get username field and change username
        composeTestRule.onNodeByTag(ChangeUsernamePrompt.TestTags.usernameField)
            .performTextReplacement(newUsername)
        // Click on "Next" which should call the prompt's [onComplete] function and
        // trigger the assertion
        composeTestRule.onNodeByTag(SignUpPromptBase.TestTags.nextButton)
            .performClick()
    }

    @Test
    fun usernameIsLoadedWithUserObjectValue() {
        val user = Mocks.mainUser
        val authContext = Mocks.authContext

        // Mount the prompt on the view
        composeTestRule.setContent {
            ChangeUsernamePrompt.Display(authContext = authContext) { /* Do nothing */ }
        }
        // Get username field and change username
        composeTestRule.onNodeWithText(user.username)
            .assertExists("A node displaying the username should exist")
    }

}