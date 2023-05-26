package com.github.leuludyha.ibdb.presentation.components.utils

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CenteredLoadingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun nonNullLabelIsDisplayed() {
        composeTestRule.setContent {
            CenteredLoading(label = "LABEL")
        }

        composeTestRule.onNodeWithText("LABEL").assertExists()
    }

    @Test
    fun nullLabelIsNotDisplayed() {
        composeTestRule.setContent {
            CenteredLoading(label = null)
        }

        composeTestRule.onNodeWithTag("centeredLoading::label").assertDoesNotExist()
    }

    @Test
    fun loadingCircleIsDisplayed() {
        composeTestRule.setContent {
            CenteredLoading()
        }

        composeTestRule.onNodeWithTag("Loading::circularProgressIndicator").assertExists()
    }
}