package com.github.leuludyha.ibdb.presentation.screen.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.util.testTag
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import kotlin.streams.toList

/**
 * For now, this screen only starts google maps and displays a few hardcoded markers at EPFL.
 */
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun GoogleMapsScreen(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val epfl = LatLng(46.520536, 6.568318)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(epfl, 15f)
    }

    val locationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    var uiSettings by remember { mutableStateOf(MapUiSettings(
        //I did my own implementation of this, I encountered problems and no documentation online
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false)) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    //This allows to get the location of the user
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var nearbyUsers by remember { mutableStateOf(listOf<LatLng>()) }

    GoogleMap(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .testTag("GoogleMaps::main"),
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState
    ) {
        EpflMarkerAndLimits()
        SatelliteMarker()

        nearbyUsers.forEach { user ->
            MarkerInfoWindowContent(
                state = MarkerState(
                    position = LatLng(
                        user.latitude,
                        user.longitude
                    )
                ),
                onInfoWindowClose = {
                    it.alpha = 0.5f
                },
                onInfoWindowClick = {
                    Toast.makeText(context, "I will send you to another screen with this user", Toast.LENGTH_SHORT).show()
                }
            ) {
                //TODO: Put a display of the books once we can get it
                Screen.BookDetails.passBookId(Mocks.work1984.id)
                Text("Here a book will be displayed")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        //This is the refresh users button
        MapsButton(Modifier.testTag("GoogleMaps::refresh_button"), Icons.Filled.Autorenew) {
            nearbyUsers = fetchNearbyUsers(cameraPositionState, context)
        }

        Spacer(modifier = Modifier.size(30.dp))

        //This is the location button
        MapsButton(Modifier.testTag("GoogleMaps::location_button"), Icons.Filled.MyLocation) {
            locationPermissionState.launchPermissionRequest()

            //Center camera on user
            if (locationPermissionState.hasPermission) {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    null
                ).addOnSuccessListener {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.fromLatLngZoom(
                                    LatLng(it.latitude, it.longitude),
                                    ZoomLevels.Street.zoom
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * An enumeration meant to store a few default zoom levels for the camera in the maps screen.
 * Bizarrely, it looks like this enum doesn't exist by default.
 * They are taken from [here](https://developers.google.com/maps/documentation/android-sdk/views#zoom).
 */
private enum class ZoomLevels (val zoom: Float) {
    World(1f),
    Continent(5f),
    City(10f),
    Street(15f),
    Buildings(20f)
}

//TODO: Should move this into a viewModel
//TODO: This has to be modified to be linked to users. I have to consult with the others the best way of doing so.
// it will be probably be changed to a user instead of a LatLng
/**
 * This function takes care of calling Firebase to find users that are registered close to
 * within the camera's view.
 */
private fun fetchNearbyUsers(cameraPositionState: CameraPositionState, context: Context): List<LatLng> {
    if(cameraPositionState.position.zoom < ZoomLevels.City.zoom) {
        Toast.makeText(context, "Try zooming a bit more, this is too broad!", Toast.LENGTH_SHORT).show()
        Log.d("Debug", "Zoom: " + cameraPositionState.position.zoom)
        return listOf()
    }

    //call firebase with bounds of the map
    val cameraBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
    //TODO: Call Firebase once it is available

    //For now, I use a few mock locations
    return Mocks.userLocationList.stream()
        .map { pair -> LatLng(pair.first, pair.second) }
        .toList()
}

/**
 * This is a button meant to imitate the location button of google maps.
 *
 * Google maps has a default implementation of this, but I encountered problems when trying to use
 * it, and unfortunately does not have a good documentation for compose. Therefore I found it easier
 * to do my own implementation.
 *
 * @param modifier Modifier that will be propagated into this button.
 * @param onClick Function that will be called when this button is clicked.
 */
@Composable
private fun MapsButton(modifier: Modifier, icon: ImageVector, onClick: () -> Unit) {
    Box(modifier = modifier
        .padding(horizontal = 10.dp)
        .offset((-20).dp, (-20).dp)) {
        Button(
            onClick = { onClick() },
            shape = CircleShape,
            modifier = modifier.size(40.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            // Inner content including an icon and a text label
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

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
private fun getDefaultEpflLimits(): List<LatLng> {
    return listOf(
        LatLng(46.522259, 6.563326),
        LatLng(46.515126, 6.560001),
        LatLng(46.518263, 6.572170),
        LatLng(46.521600, 6.571801),
        LatLng(46.522259, 6.563326)
    )
}
