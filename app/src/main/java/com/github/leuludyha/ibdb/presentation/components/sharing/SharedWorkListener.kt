package com.github.leuludyha.ibdb.presentation.components.sharing

import android.Manifest
import android.R
import android.os.Build
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
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

    val (connectedEndpointName, setConnectedEndpointName) = remember { mutableStateOf("") }

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
                setConnectedEndpointName(endpointId)
                if (viewModel.connection.isAdvertising()) {
                    viewModel.connection.stopAdvertising()
                }
            }

            override fun onDisconnected(endpointId: String) {
                setConnectedEndpointName("")
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
                endpointName = connectedEndpointName,
                packet = packet,
                navController = navController,
                onProcessed = {
                    if (viewModel.connection.isConnected()) {
                        viewModel.connection.disconnect()
                    }
                    setState(ListenerState.Listening)
                }
            )
        }

        else -> {}
    }
}

@Composable
private fun PacketProcessor(
    endpointName: String,
    packet: NearbyMsgPacket,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    when (packet.prefix) {
        NearbyMsgPacket.ShareWork.id -> {
            ProcessShareWorkPacket(
                endpointName = endpointName,
                workId = packet.content,
                navController = navController,
                onProcessed = onProcessed
            )
        }

        NearbyMsgPacket.AddFriend.id -> {
            ProcessAddFriendPacket(
                endpointName = endpointName,
                navController = navController,
                onProcessed = onProcessed
            )
        }

        else -> {
            throw UnsupportedOperationException(
                "Unsupported packet : " +
                        "\n Prefix: \"${packet.prefix}\"" +
                        "\n Content: \"${packet.descriptor}\""
            )
        }
    }
}

@Composable
private fun ProcessShareWorkPacket(
    endpointName: String,
    workId: String,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.zIndex(1000f),
        onDismissRequest = { onProcessed() },
        title = { Text("Accept $endpointName's sharing") },
        text = { Text(text = "$endpointName wants to share a book with you") },
        confirmButton = {
            Button(onClick = {
                navController.navigate(Screen.BookDetails.passBookId(workId))
                onProcessed()
            }) { Text(text = "Accept") }
        },
        dismissButton = {
            Button(onClick = {
                // The user canceled, so we should reject the connection.
                onProcessed()
                TODO("Not yet implemented")
            }) { Text(text = "Refuse") }
        },
        icon = { R.drawable.ic_dialog_alert }
    )

}

@Composable
private fun ProcessAddFriendPacket(
    endpointName: String,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.zIndex(1000f),
        onDismissRequest = { onProcessed() },
        title = { Text("Accept $endpointName's friend request") },
        text = { Text(text = "$endpointName wants to add you as their friend") },
        confirmButton = {
            Button(onClick = {
                onProcessed()
                TODO("Not yet implemented")
            }) { Text(text = "Accept") }
        },
        dismissButton = {
            Button(onClick = {
                // The user canceled, so we should reject the connection.
                onProcessed()
                TODO("Not yet implemented")
            }) { Text(text = "Refuse") }
        },
        icon = { R.drawable.ic_dialog_alert }
    )
}