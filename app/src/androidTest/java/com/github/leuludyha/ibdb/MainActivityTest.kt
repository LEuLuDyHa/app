package com.github.leuludyha.ibdb

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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