package com.github.leuludyha.ibdb.presentation.screen.maps

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.useCase.users.GetNearbyUsersUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

@HiltViewModel
class GoogleMapsScreenViewModel @Inject constructor(
    private var useCase: GetNearbyUsersUseCase
) : ViewModel() {

    //Epfl's location
    val defaultLocation = LatLng(46.520536, 6.568318)

    val nearbyUsers = mutableStateOf(listOf<User>())

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

    /**
     * This function takes care of calling Firebase to find users that are registered close to
     * within the camera's view. When the camera is zoomed out too much, it returns an empty list.
     */
    fun fetchNearbyUsers(
        cameraPositionState: CameraPositionState,
        context: Context
    ): CompletableFuture<List<User>> {
        if (cameraPositionState.position.zoom < ZoomLevels.City.zoom) {
            Toast.makeText(
                context,
                "Try zooming a bit more, this is too broad!",
                Toast.LENGTH_SHORT
            )
                .show()
            return CompletableFuture.completedFuture(listOf())
        }

        //call firebase with bounds of the map
        val cameraBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds

        return if (cameraBounds != null) {
            useCase.invoke(
                cameraBounds.northeast.latitude,
                cameraBounds.northeast.longitude,
                cameraBounds.southwest.latitude,
                cameraBounds.southwest.longitude
            )
        } else {
            //The camera bounds have not been initialized yet (map didn't load)
            //Since the map isn't loaded yet, no point on fetching any value to display on it
            CompletableFuture.completedFuture(listOf())
        }
    }
}