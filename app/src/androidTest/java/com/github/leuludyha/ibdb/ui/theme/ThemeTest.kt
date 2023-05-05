package com.github.leuludyha.ibdb.ui.theme

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun themeDoesNotCrashWithEmptyContent() {
        composeTestRule.setContent { 
            IBDBTheme(darkTheme = false, dynamicColor = false) {

            }
        }
    }

    @Test
    fun themeDoesNotCrashWithDynamicColor() {
        composeTestRule.setContent {
            IBDBTheme(darkTheme = true, dynamicColor = true) {

            }
        }
    }
}