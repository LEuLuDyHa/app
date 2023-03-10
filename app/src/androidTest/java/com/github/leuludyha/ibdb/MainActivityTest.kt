package com.github.leuludyha.ibdb

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun enteringUsernameAndPressingStartSendsUsernameIntent() {
        Intents.init()

        val username = "Super Cool Username"

        // Click on text and input username
        composeTestRule.onNode(hasTestTag("main::text_field"))
            .performClick()
            .performTextInput(username)

        // Click on button
        composeTestRule.onNode(hasTestTag("main::start_button"))
            .performClick()

        intended(hasExtra(GreetingActivity.DeclaredIntents.username, username))
        intended(hasComponent(GreetingActivity::class.qualifiedName))

        Intents.release()
    }

}