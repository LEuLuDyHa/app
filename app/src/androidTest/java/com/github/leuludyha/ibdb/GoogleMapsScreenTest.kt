package com.github.leuludyha.ibdb

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreen
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreenViewModel
import org.junit.Rule
import org.junit.Test

class GoogleMapsScreenTest {

    //These tests are rather limited and no marker is properly tested.
    //I can't seem to find any way of testing any markers or anything inside the
    //GoogleMap composable, so I can just check what is not inside it (nothing)

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Test
    fun clickingOnLocationButtonDoesNotCrash() {
        //It doesn't look like there is any way of accessing markers from the tests,
        //so no way of checking that markers appeared or anything like that.
        composeTestRule.setContent {
            GoogleMapsScreen(
                navController = rememberNavController(),
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag("GoogleMaps::location_button")
            .performClick()

        composeTestRule.waitForIdle()
    }

    @Test
    fun clickingOnRefreshButtonDoesNotCrash() {
        composeTestRule.setContent {
            GoogleMapsScreen(
                navController = rememberNavController(),
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel()
            )
        }

        composeTestRule
            .onNodeWithTag("GoogleMaps::refresh_button")
            .performClick()

        composeTestRule.waitForIdle()
    }

    //TODO: This test has to be refined by implementing a mock viewModel once Firebase is working
    @Test
    fun clickingOnRefreshButtonCreatesNewMarkersUnderMainComposable() {
        val viewModel = GoogleMapsScreenViewModel()

        composeTestRule.setContent {
            GoogleMapsScreen(
                navController = rememberNavController(),
                paddingValues = PaddingValues(0.dp),
                viewModel = viewModel
            )
        }

//        composeTestRule.onRoot(true).printToLog("Debug", maxDepth = 10)

        val oldNumberOfNearbyUsers = viewModel.nearbyUsers.value

        composeTestRule
            .onNodeWithTag("GoogleMaps::refresh_button")
            .performClick()

        composeTestRule.waitForIdle()

        assert(oldNumberOfNearbyUsers.size < viewModel.nearbyUsers.value.size)
    }
}
