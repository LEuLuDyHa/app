package com.github.leuludyha.ibdb.presentation.components.sharing

import android.Manifest
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
import com.github.leuludyha.domain.model.authentication.Endpoint
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
                "Sharing permissions not granted !"
            )
        },
        permissionsNotAvailableContent = {
            Log.w(
                "PERMISSIONS",
                "Sharing permissions not available !"
            )
        },
        content = { SharedWorkListenerComponent(navController = navController) }
    )

}

@Composable
private fun SharedWorkListenerComponent(
    viewModel: SharedWorkListenerViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val (state, setState) = remember { mutableStateOf(ListenerState.Listening) }
    val (packet, setPacket) = remember { mutableStateOf<NearbyMsgPacket?>(null) }

    val (connectedEndpoint, setConnectedEndpoint) = remember { mutableStateOf<Endpoint?>(null) }


    DisposableEffect(viewModel.connection) {

        val handler = object : ConnectionLifecycleHandler() {
            override fun onMount() {
                if (!viewModel.connection.isAdvertising()) {
                    viewModel.connection.startAdvertising()
                }
            }

            // Pause/Restart advertising when discovery is started : We can only have
            // one connection anyway
            override fun onDiscoveryStarted() {
                viewModel.connection.stopAdvertising()
            }

            override fun onDiscoveryStopped() {
                if (!viewModel.connection.isAdvertising()) {
                    viewModel.connection.startAdvertising()
                }
            }

            // Pause/Restart advertising when connected to an endpoint : We can only have
            // one connection anyway
            override fun onConnected(endpoint: Endpoint) {
                setConnectedEndpoint(endpoint)
                viewModel.connection.stopAdvertising()
            }

            override fun onDisconnected(endpointId: String) {
                setConnectedEndpoint(null)
                setPacket(null)
                if (!viewModel.connection.isAdvertising()) {
                    viewModel.connection.startAdvertising()
                }
            }

            override fun onPacketReceived(packet: NearbyMsgPacket) {
                setPacket(packet)
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

        // Once this component is removed from the composition,
        // Remove the connection's listener
        onDispose { viewModel.connection.removeListener(handler) }
    }

    when (state) {
        // If a packet is received, process it
        ListenerState.Received -> {
            PacketProcessor(
                endpoint = connectedEndpoint!!,
                packet = packet!!,
                navController = navController,
                onProcessed = {
                    if (viewModel.connection.isConnected()) {
                        viewModel.connection.disconnect()
                    }
                    setState(ListenerState.Listening)
                }
            )
        }

        else -> { /* Show nothing */
        }
    }
}

@Composable
private fun PacketProcessor(
    endpoint: Endpoint,
    packet: NearbyMsgPacket,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    when (packet.prefix) {
        NearbyMsgPacket.ShareWork.id -> {
            ProcessShareWorkPacket(
                endpoint = endpoint,
                workId = packet.content,
                navController = navController,
                onProcessed = onProcessed
            )
        }

        NearbyMsgPacket.AddFriend.id -> {
            ProcessAddFriendPacket(
                endpoint = endpoint,
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
    endpoint: Endpoint,
    workId: String,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.zIndex(1000f),
        onDismissRequest = { onProcessed() },
        title = { Text("Accept ${endpoint.name}'s sharing") },
        text = { Text(text = "${endpoint.name} wants to share a book with you") },
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
        icon = { android.R.drawable.ic_dialog_alert }
    )

}

@Composable
private fun ProcessAddFriendPacket(
    endpoint: Endpoint,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.zIndex(1000f),
        onDismissRequest = { onProcessed() },
        title = { Text("Accept ${endpoint.name}'s friend request") },
        text = { Text(text = "${endpoint.name} wants to add you as their friend") },
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
        icon = { android.R.drawable.ic_dialog_alert }
    )
}