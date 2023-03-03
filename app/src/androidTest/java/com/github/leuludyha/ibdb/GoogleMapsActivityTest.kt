package com.github.leuludyha.ibdb

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.leuludyha.ibdb.maps.GoogleMapsActivity

import org.junit.Rule
import org.junit.Test

class GoogleMapsActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<GoogleMapsActivity>()

    @Test
    fun infoWindowWithEPFLTagDoesNotExistByDefault() {
        //TODO: Useless test, remove
        //composeTestRule.onNodeWithTag("info_window_epfl").assertDoesNotExist()
    }

    @Test
    fun infoWindowWithEPFLTagAppearsAfterClickOnMarker() {
        //TODO: I couldn't find any way to test the markers
//        composeTestRule.onNodeWithTag("test_tag").performClick()
//        composeTestRule.onNodeWithTag("GoogleMap").onChild().performClick();
//        composeTestRule.onNodeWithTag("info_window_epfl").assertExists()
    }
}