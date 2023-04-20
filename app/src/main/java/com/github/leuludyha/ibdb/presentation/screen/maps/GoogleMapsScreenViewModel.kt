package com.github.leuludyha.ibdb.presentation.screen.maps

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.library.Mocks
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.streams.toList

@HiltViewModel
class GoogleMapsScreenViewModel @Inject constructor(
    //private var getUserFromPhoneNumberUseCase: GetUserFromPhoneNumberUseCase
) : ViewModel() {

    //Epfl's location
    val defaultLocation = LatLng(46.520536, 6.568318)

    //TODO: Modify to users when ready
    val nearbyUsers = mutableStateOf(listOf<LatLng>())

    /**
     * An enumeration meant to store a few default zoom levels for the camera in the maps screen.
     * Bizarrely, it looks like this enum doesn't exist by default.
     * They are taken from [here](https://developers.google.com/maps/documentation/android-sdk/views#zoom).
     */
    enum class ZoomLevels(val zoom: Float) {
        World(1f),
        Continent(5f),
        City(10f),
        Street(15f),
        Buildings(20f)
    }

    //TODO: This has to be modified to be linked to users. I have to consult with the others the best way of doing so.
    // it will be probably be changed to a user instead of a LatLng
    /**
     * This function takes care of calling Firebase to find users that are registered close to
     * within the camera's view. When the camera is zoomed out too much, it returns an empty list.
     */
    fun fetchNearbyUsers(
        cameraPositionState: CameraPositionState,
        context: Context
    ): List<LatLng> {
        if (cameraPositionState.position.zoom < ZoomLevels.City.zoom) {
            Toast.makeText(
                context,
                "Try zooming a bit more, this is too broad!",
                Toast.LENGTH_SHORT
            )
                .show()
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
}