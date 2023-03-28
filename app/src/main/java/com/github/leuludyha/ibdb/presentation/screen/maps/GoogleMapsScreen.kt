package com.github.leuludyha.ibdb.presentation.screen.maps

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.github.leuludyha.ibdb.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * For now, this screen only starts google maps and displays a few hardcoded markers at EPFL.
 */
@Composable
fun GoogleMapsScreen(
    navController: NavHostController,
    initialLatitude: String?, //These are treated as strings because they are Double, but navController only accepts floats
    initialLongitude: String?,
    interestPoints: Array<String>?
) {
    //This below will be replaced by the current user's location later.
    val epfl = LatLng(46.520536, 6.568318)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(epfl, 15f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .testTag("GoogleMaps::main"),
        cameraPositionState = cameraPositionState
    ) {
        EpflMarkerAndLimits()
        SatelliteMarker()
    }
}

/**
 * Draws a marker at EPFL (hardcoded) location, that will draw its limits when clicked upon.
 */
@Composable
private fun EpflMarkerAndLimits() {
    val epfl = LatLng(46.520536, 6.568318)
    val epflLimits = getDefaultEpflLimits()
    val showEpflLimits = remember { mutableStateOf(false) }

    MarkerInfoWindowContent(
        tag = "test_tag",
        state = MarkerState(position = epfl),
        onInfoWindowClick = {
            showEpflLimits.value = true
        },
        onInfoWindowClose = {
            showEpflLimits.value = false
        },
        content = {
            Column(
                modifier = Modifier.testTag("info_window_epfl"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "EPFL", fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.title_activity_google_maps))
                Text(text = "Click to see its limits!")
            }
        }
    )
    Polygon(
        points = epflLimits,
        visible = showEpflLimits.value,
        strokeColor = Color.Green,
        strokeWidth = 6f,
        fillColor = Color.Green.copy(0.3f)
    )
}

/**
 * Draws a marker at satellite (hardcoded) location that will generate a toast when clicking
 * on the info window.
 */
@Composable
private fun SatelliteMarker() {
    val satellite = LatLng(46.520544, 6.567825)
    val context = LocalContext.current

    Marker(
        state = MarkerState(position = satellite),
        title = "Satellite",
        snippet = "Cool place!",
        onInfoWindowClick = {
            Toast.makeText(context, "Coor: $satellite", Toast.LENGTH_SHORT).show()
        }
    )
}

/**
 * Returns hardcoded EPFL limits in a list.
 */
private fun getDefaultEpflLimits() : List<LatLng> {
    return listOf(
        LatLng(46.522259, 6.563326),
        LatLng(46.515126, 6.560001),
        LatLng(46.518263, 6.572170),
        LatLng(46.521600, 6.571801),
        LatLng(46.522259, 6.563326)
    )
}
