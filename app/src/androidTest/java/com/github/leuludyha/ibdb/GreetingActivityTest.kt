package com.github.leuludyha.ibdb

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import launch
import org.junit.Rule
import org.junit.Test


class GreetingActivityTest {

    @get:Rule
    val composeRule = createEmptyComposeRule()

    @Test
    fun usernameIsDisplayedOnActivityStartedWithIntent() {
        val username = "Very cool username"

        composeRule.launch<GreetingActivity> (
            onBefore = { },
            intentFactory = {
                Intent(it, GreetingActivity::class.java).apply {
                    putExtra(GreetingActivity.DeclaredIntents.username, username)
                }
            },
            onAfterLaunched = {
                // Assertions on the view
                onNodeWithText("Hello $username!").assertIsDisplayed()
            }
        )
    }
}