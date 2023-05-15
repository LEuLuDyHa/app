package com.github.leuludyha.ibdb.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.MockUserRepositoryImpl
import com.github.leuludyha.domain.useCase.users.GetNearbyUsersUseCase
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreen
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreenViewModel
import com.github.leuludyha.ibdb.presentation.screen.maps.getDefaultEpflLimits
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoogleMapsScreenTest {

    //These tests are rather limited and no marker is properly tested.
    //I can't seem to find any way of testing any markers or anything inside the
    //GoogleMap composable, so I can just check what is not inside it (barely anything)

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    //The useCase can be used on multiple test but careful! The viewModels have to be recreated for each test!
    //They cannot be shared
    private val injectedUseCase: GetNearbyUsersUseCase = GetNearbyUsersUseCase(MockUserRepositoryImpl())

    @Test
    fun clickingOnLocationButtonDoesNotCrash() {
        //It doesn't look like there is any way of accessing markers from the tests,
        //so no way of checking that markers appeared or anything like that.
        composeTestRule.setContent {
            GoogleMapsScreen(
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel(injectedUseCase)
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("GoogleMaps::location_button")
            .performClick()

        composeTestRule.waitForIdle()
    }

    @Test
    fun clickOnMapDoesNotCrash() {
        //It doesn't look like there is any way of accessing markers from the tests,
        //so no way of checking that markers appeared or anything like that.
        composeTestRule.setContent {
            GoogleMapsScreen(
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel(injectedUseCase),
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("GoogleMaps::main").performClick()
    }

    @Test
    fun clickingOnRefreshButtonDoesNotCrash() {
        composeTestRule.setContent {
            GoogleMapsScreen(
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel(injectedUseCase)
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("GoogleMaps::refresh_button")
            .performClick()

        composeTestRule.waitForIdle()
    }

    @Test
    fun clickingOnRefreshButtonIncreasesOrMaintainsNumberOfMarkers() {
        val viewModel = GoogleMapsScreenViewModel(GetNearbyUsersUseCase(MockUserRepositoryImpl()))

        composeTestRule.setContent {
            GoogleMapsScreen(
                paddingValues = PaddingValues(0.dp),
                viewModel = viewModel
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

//        composeTestRule.onRoot(true).printToLog("Debug", maxDepth = 10)

        val oldNumberOfNearbyUsers = viewModel.nearbyUsers.value

        composeTestRule
            .onNodeWithTag("GoogleMaps::refresh_button")
            .performClick()

        composeTestRule.waitForIdle()

        assert(oldNumberOfNearbyUsers.size <= viewModel.nearbyUsers.value.size)
    }

    @Test
    fun epflLimitsAreCorrect() {
        assertThat(getDefaultEpflLimits()).isEqualTo(listOf(
            LatLng(46.522259, 6.563326),
            LatLng(46.515126, 6.560001),
            LatLng(46.518263, 6.572170),
            LatLng(46.521600, 6.571801),
            LatLng(46.522259, 6.563326)
        ))
    }
}
