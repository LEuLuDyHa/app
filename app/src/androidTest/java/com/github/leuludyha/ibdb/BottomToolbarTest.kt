package com.github.leuludyha.ibdb

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import clickOnBottomTab
import com.github.leuludyha.ibdb.presentation.navigation.Maps
import com.github.leuludyha.ibdb.presentation.navigation.Search
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomToolbarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun mapsToolbarSlotOpensGoogleMapsComposable() {
        composeTestRule
            .onNodeWithTag("GoogleMaps::main")
            .assertDoesNotExist()

        composeTestRule
            .clickOnBottomTab(Maps)

        composeTestRule
            .onNodeWithTag("GoogleMaps::main")
            .assertExists()
    }

    @Test
    fun searchToolbarSlotOpensSearchComposableWithSearchField() {
        composeTestRule
            .clickOnBottomTab(Search)

        composeTestRule
            .onNodeWithTag("book_search::search_field")
            .assertExists()
    }

    @Test
    fun searchToolbarSlotOpensSearchComposableWithScanButton() {
        composeTestRule
            .clickOnBottomTab(Search)

        composeTestRule
            .onNodeWithTag("book_search::barcode_scan_button")
            .assertExists()
    }
}
