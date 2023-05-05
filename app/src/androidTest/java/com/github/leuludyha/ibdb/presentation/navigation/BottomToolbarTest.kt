package com.github.leuludyha.ibdb.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomToolbarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeTabDescriptorIsPresent() {
        composeTestRule.setContent {
            BottomToolbar(navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context))
        }

        composeTestRule.onNodeWithTag("bottomtoolbar::tab_item::${TabDescriptor.Home.displayName}")
            .assertExists()
    }

    @Test
    fun searchTabDescriptorIsPresent() {
        composeTestRule.setContent {
            BottomToolbar(navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context))
        }

        composeTestRule.onNodeWithTag("bottomtoolbar::tab_item::${TabDescriptor.Search.displayName}")
            .assertExists()
    }

    @Test
    fun collectionTabDescriptorIsNotPresent() {
        composeTestRule.setContent {
            BottomToolbar(navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context))
        }

        composeTestRule.onNodeWithTag("bottomtoolbar::tab_item::${TabDescriptor.Collection.displayName}")
            .assertDoesNotExist()
    }

    @Test
    fun mapsTabDescriptorIsPresent() {
        composeTestRule.setContent {
            BottomToolbar(navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context))
        }

        composeTestRule.onNodeWithTag("bottomtoolbar::tab_item::${TabDescriptor.Maps.displayName}")
            .assertExists()
    }

    @Test
    fun profileTabDescriptorIsPresent() {
        composeTestRule.setContent {
            BottomToolbar(navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context))
        }

        composeTestRule.onNodeWithTag("bottomtoolbar::tab_item::${TabDescriptor.Profile.displayName}")
            .assertExists()
    }

    @Test
    fun homeTabDescriptorHasCorrectIcon() {
        assertThat(TabDescriptor.Home.displayIcon).isEqualTo(Icons.Filled.Home)
    }

    @Test
    fun searchTabDescriptorHasCorrectIcon() {
        assertThat(TabDescriptor.Search.displayIcon).isEqualTo(Icons.Filled.Search)
    }

    @Test
    fun collectionTabDescriptorHasCorrectIcon() {
        assertThat(TabDescriptor.Collection.displayIcon).isEqualTo(Icons.Filled.LibraryBooks)
    }

    @Test
    fun mapsTabDescriptorHasCorrectIcon() {
        assertThat(TabDescriptor.Maps.displayIcon).isEqualTo(Icons.Filled.Map)
    }

    @Test
    fun profileTabDescriptorHasCorrectIcon() {
        assertThat(TabDescriptor.Profile.displayIcon).isEqualTo(Icons.Filled.AccountCircle)
    }

    private fun tabClickDoesNotCrashOnNullNavHostController(tabDescriptor: TabDescriptor) {
        composeTestRule.setContent {
            BottomToolbar(navController = null)
        }

        composeTestRule.onNodeWithTag("bottomtoolbar::tab_item::${tabDescriptor.displayName}")
            .performClick()
        composeTestRule.waitForIdle()

        assertThat(true).isEqualTo(true)
    }

    @Test
    fun homeTabClickDoesNotCrashOnNullNavHostController() =
        tabClickDoesNotCrashOnNullNavHostController(TabDescriptor.Home)

    @Test
    fun searchTabClickDoesNotCrashOnNullNavHostController() =
        tabClickDoesNotCrashOnNullNavHostController(TabDescriptor.Search)

    @Test
    fun mapsTabClickDoesNotCrashOnNullNavHostController() =
        tabClickDoesNotCrashOnNullNavHostController(TabDescriptor.Maps)

    @Test
    fun profileTabClickDoesNotCrashOnNullNavHostController() =
        tabClickDoesNotCrashOnNullNavHostController(TabDescriptor.Profile)
}