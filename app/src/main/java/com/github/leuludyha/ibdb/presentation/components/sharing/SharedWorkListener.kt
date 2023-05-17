package com.github.leuludyha.ibdb.presentation.components.sharing

import android.Manifest
import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState


private enum class ListenerState {
    Listening, Received
}

/**
 * Listens to nearby user who want to share a work
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SharedWorkListener(
    navController: NavHostController,
) {

    Log.i("VERSION", Build.VERSION.SDK_INT.toString())

    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.NEARBY_WIFI_DEVICES
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
                "Sharing permissions not granted !"
            )
        },
        permissionsNotAvailableContent = {
            Log.w(
                "PERMISSIONS",
                "Sharing permissions not available !"
            )
        })
    {
        SharedWorkListenerComponent(navController = navController)
    }

}

@Composable
private fun SharedWorkListenerComponent(
    viewModel: SharedWorkListenerViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val (state, setState) = remember { mutableStateOf(ListenerState.Listening) }
    val (packet, setPacket) = remember { mutableStateOf(NearbyMsgPacket("empty")) }

    val (connectedEndpointId, setConnectedEndpointId) = remember { mutableStateOf("") }

    DisposableEffect(viewModel.connection) {

        val handler = object : ConnectionLifecycleHandler() {
            override fun onMount() {
                Log.i("SHARE", "ADVERTISING STARTED")
                if (!viewModel.connection.isAdvertising()) {
                    viewModel.connection.startAdvertising()
                }
            }

            // Pause/Restart advertising when discovery is started : We can only have
            // one connection anyway
            override fun onDiscoveryStarted() {
                if (viewModel.connection.isAdvertising()) {
                    viewModel.connection.stopAdvertising()
                }
            }

            override fun onDiscoveryStopped() {
                if (!viewModel.connection.isAdvertising()) {
                    viewModel.connection.startAdvertising()
                }
            }

            // Pause/Restart advertising when connected to an endpoint : We can only have
            // one connection anyway
            override fun onConnected(endpointId: String) {
                setConnectedEndpointId(endpointId)
                if (viewModel.connection.isAdvertising()) {
                    viewModel.connection.stopAdvertising()
                }
            }

            override fun onDisconnected(endpointId: String) {
                setConnectedEndpointId("")
                setState(ListenerState.Listening)
                if (!viewModel.connection.isAdvertising()) {
                    viewModel.connection.startAdvertising()
                }
            }

            override fun onPacketReceived(packet: NearbyMsgPacket) {
                setPacket(packet)
                Log.i("SHARE", "RECEIVED")
                setState(ListenerState.Received)
            }

            override fun onError(description: String) {
                throw Error(description)
            }

            override fun onDismount() {
                // Stop listening and stop advertising
                if (viewModel.connection.isAdvertising()) {
                    viewModel.connection.stopAdvertising()
                }
                // And disconnect if the connection is active
                if (viewModel.connection.isConnected()) {
                    viewModel.connection.disconnect()
                }
            }
        }

        // Listen to the connection's events and start advertising
        viewModel.connection.addListener(handler)

        onDispose { viewModel.connection.removeListener(handler) }
    }

    when (state) {
        // If a packet is received, process it
        ListenerState.Received -> {
            PacketProcessor(
                endpointId = connectedEndpointId,
                packet = packet,
                navController = navController,
                onProcessed = { viewModel.connection.disconnect() }
            )
        }

        else -> {}
    }
}

@Composable
private fun PacketProcessor(
    endpointId: String,
    packet: NearbyMsgPacket,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    when (packet.prefix) {
        NearbyMsgPacket.ShareWork.id -> {
            ProcessShareWorkPacket(
                endpointId = endpointId,
                workId = packet.content,
                navController = navController,
                onProcessed = onProcessed
            )
        }

        NearbyMsgPacket.AddFriend.id -> {
            throw NotImplementedError()
        }
    }
}

@Composable
private fun ProcessShareWorkPacket(
    endpointId: String,
    workId: String,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    AlertDialog.Builder(LocalContext.current)
        .setTitle("Accept $endpointId's sharing")
        .setMessage("$endpointId wants to share a book with you")
        .setPositiveButton("Accept") { dialog: DialogInterface?, which: Int ->
            // The user confirmed, so we can accept the work's sharing.
            navController.navigate(Screen.BookDetails.passBookId(workId))
            onProcessed()
        }
        .setNegativeButton(R.string.cancel) { dialog: DialogInterface?, which: Int ->
            // The user canceled, so we should reject the connection.
            TODO("Not yet implemented")
        }
        .setIcon(R.drawable.ic_dialog_alert)
        .show()

}

@Composable
private fun ProcessAddFriendPacket(
    endpointId: String,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    AlertDialog.Builder(LocalContext.current)
        .setTitle("Accept $endpointId's friend request")
        .setMessage("$endpointId wants to add you as their friend")
        .setPositiveButton("Accept") { dialog: DialogInterface?, which: Int ->
            // The user confirmed, so we can accept the connection.
            TODO("Not yet implemented")
        }
        .setNegativeButton("Refuse") { dialog: DialogInterface?, which: Int ->
            // The user canceled, so we should reject the connection.
            TODO("Not yet implemented")
        }
        .setIcon(R.drawable.ic_dialog_alert)
        .show()

}