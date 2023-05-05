package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks.mainUser
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MiniContactFoundViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profilePictureExists() {
        // Mount the prompt on the view
        composeTestRule.setContent {
            MiniContactFoundView(contactName = "MY_FRIEND", user = mainUser)
        }

        composeTestRule.onNodeWithTag("profile_picture").assertExists()
    }

    @Test
    fun contactNameIsPresent() {
        // Mount the prompt on the view
        composeTestRule.setContent {
            MiniContactFoundView(contactName = "MY_FRIEND", user = mainUser)
        }

        composeTestRule.onNodeWithText("MY_FRIEND").assertExists()
    }

    @Test
    fun usernameIsPresent() {
        // Mount the prompt on the view
        composeTestRule.setContent {
            MiniContactFoundView(contactName = "MY_FRIEND", user = mainUser)
        }

        composeTestRule.onNodeWithText(mainUser.username).assertExists()
    }

    @Test
    fun clickOnSendRequestButtonDisablesIt() {
        // Mount the prompt on the view
        composeTestRule.setContent {
            MiniContactFoundView(contactName = "MY_FRIEND", user = mainUser)
        }

        composeTestRule
            .onNodeWithTag("send_request_button")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("send_request_button")
            .assertIsNotEnabled()
    }

    @Test
    fun sendRequestButtonIsEnabledFirst() {
        // Mount the prompt on the view
        composeTestRule.setContent {
            MiniContactFoundView(contactName = "MY_FRIEND", user = mainUser)
        }

        composeTestRule
            .onNodeWithTag("send_request_button")
            .assertIsEnabled()
    }
}