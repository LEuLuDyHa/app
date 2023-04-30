package com.github.leuludyha.ibdb.presentation.screen.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

/**
 * This screen opens a google maps composable. Currently implemented features include user
 * localisation and nearby users display. When clicking on a marker, it can open the actual
 * google maps app to propose a route to get to the marker.
 */
@Composable
fun GoogleMapsScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: GoogleMapsScreenViewModel = hiltViewModel()
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.defaultLocation, 15f)
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                //I did my own implementation of this location button,
                //I encountered problems and no documentation online with the default solution
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false
            )
        )
    }
    val context = LocalContext.current
    val nearbyUsers by viewModel.nearbyUsers

    LaunchedEffect(Unit) {
        viewModel.fetchNearbyUsers(cameraPositionState, context).thenAccept {
            viewModel.nearbyUsers.value = it
        }
    }

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
            BookSharingMarker(user = user, context = context)
        }
    }

    MapsUI(
        viewModel = viewModel,
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        context = context
    )
}

/**
 * This is the UI that will be used on top the map. It consists of a composable that should be superimposed
 * onto the map. Right now, it contains two buttons for location and refreshing.
 * @param viewModel of the maps screen
 * @param paddingValues of the maps screen (make sure they are the same so that it properly is on top of the map!
 * @param cameraPositionState of the map. It might be updated from calls to the UI
 * @param context of the map
 */
@OptIn(ExperimentalPermissionsApi::class)
//This permission check is actually done in the code, but for some reason the compiler cries about it not being done without suppressing it.
@SuppressLint("MissingPermission")
@Composable
private fun MapsUI(
    viewModel: GoogleMapsScreenViewModel,
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    context: Context
) {
    //This allows to get the location of the user
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        //This is the refresh users button
        MapsButton(Modifier.testTag("GoogleMaps::refresh_button"), Icons.Filled.Autorenew) {
            viewModel.fetchNearbyUsers(cameraPositionState, context).thenAccept {
                //TODO: Remove the mock users from the list once we can add users to Firebase
                // for testing and examples
                viewModel.nearbyUsers.value = it.plus(Mocks.userList)
            }
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
                                    GoogleMapsScreenViewModel.ZoomLevels.Street.zoom
                                )
                            )
                        )
                    }
                }
            }
        }

        //This spacer is here to allow for google commands to appear when clicking on a marker
        Spacer(modifier = Modifier.size(50.dp))
    }
}

/**
 * This is maps compose marker meant to display books when clicked on.
 *
 * @param user whose books and location will be used for the marker.
 * @param context of the map
 */
@Composable
private fun BookSharingMarker(
    user: User,
    context: Context
) {
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
            Toast.makeText(
                context,
                "I will send you to another screen with this user's info",
                Toast.LENGTH_SHORT
            ).show()
        }
    ) {
        //TODO: Put a display of all the books from a given user in a scrollable view.
        // Right now users do not have their stored books willing to be shared
        // For some reason, MiniBookView doesn't work here.
        MiniBookView(
            work = Mocks.workLaFermeDesAnimaux,
            onClick = {},
            orientation = Orientation.Horizontal
        )
    }
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
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .offset((-20).dp, (-20).dp)
    ) {
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
