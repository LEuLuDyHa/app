package com.github.leuludyha.ibdb.presentation.screen

import android.os.Looper
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.MockUserRepositoryImpl
import com.github.leuludyha.domain.useCase.users.GetNearbyUsersUseCase
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreenViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import com.google.maps.android.compose.CameraPositionState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoogleMapsScreenViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Before
    fun setup() {
        if(Looper.myLooper() == null)
            Looper.prepare()
    }

    @Test
    fun fetchNearbyUsersReturnsEmptyListOnTooSmallZoom() {
        val viewModel = GoogleMapsScreenViewModel(
            GetNearbyUsersUseCase(MockUserRepositoryImpl())
        )

        val nearbyUsers = viewModel.fetchNearbyUsers(
            cameraPositionState = CameraPositionState(CameraPosition.fromLatLngZoom(LatLng(10.0, 10.0), 2f)),
            context = InstrumentationRegistry.getInstrumentation().context
        ).get()

        assertThat(nearbyUsers).isEmpty()
    }

    @Test
    fun fetchNearbyUsersReturnsEmptyListOnMapNotLoaded() {
        val viewModel = GoogleMapsScreenViewModel(
            GetNearbyUsersUseCase(MockUserRepositoryImpl())
        )

        val nearbyUsers = viewModel.fetchNearbyUsers(
            cameraPositionState = CameraPositionState(CameraPosition.fromLatLngZoom(LatLng(10.0, 10.0), 16f)),
            context = InstrumentationRegistry.getInstrumentation().context
        ).get()

        assertThat(nearbyUsers).isEmpty()
    }

    @Test
    fun zoomLevelsAreAtCorrectZoom() {
        assertThat(GoogleMapsScreenViewModel.ZoomLevels.World.zoom).isEqualTo(1f)
        assertThat(GoogleMapsScreenViewModel.ZoomLevels.Continent.zoom).isEqualTo(5f)
        assertThat(GoogleMapsScreenViewModel.ZoomLevels.City.zoom).isEqualTo(10f)
        assertThat(GoogleMapsScreenViewModel.ZoomLevels.Street.zoom).isEqualTo(15f)
        assertThat(GoogleMapsScreenViewModel.ZoomLevels.Buildings.zoom).isEqualTo(20f)
    }

    @Test
    fun defaultLocationIsAtEpfl() {
        val viewModel = GoogleMapsScreenViewModel(
            GetNearbyUsersUseCase(MockUserRepositoryImpl())
        )

        assertThat(viewModel.defaultLocation).isEqualTo(LatLng(46.520536, 6.568318))
    }
}