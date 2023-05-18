package com.github.leuludyha.ibdb.presentation.components.sharing

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.res.stringResource
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.utils.CenteredText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

/**
 * Composable which handles all the permission logic for the
 * sharing functionality
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SharingPermissionRequired(

    content: (@Composable () -> Unit)
) {
    val permissionState = rememberMultiplePermissionsState(
        // Only add permissions allowed by the system's version
        // -> NearbyConnection API handles cases all cases :
        // It reduces its functionalities depending on the granted permissions
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ).plus(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) listOf(
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
            ) else emptyList()
        ).plus(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) listOf(
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) else emptyList()
        )
    )

    SideEffect {
        if (!permissionState.allPermissionsGranted) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            Log.w(
                "PERMISSIONS",
                stringResource(id = R.string.permission_sharing_not_granted)
            )
            CenteredText(text = stringResource(id = R.string.permission_sharing_not_granted))
        },
        permissionsNotAvailableContent = {
            Log.w(
                "PERMISSIONS",
                stringResource(id = R.string.permission_sharing_not_available)
            )
            CenteredText(text = stringResource(id = R.string.permission_sharing_not_available))
        },
        content = content
    )
}