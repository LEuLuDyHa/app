package com.github.leuludyha.ibdb.presentation.components.sharing

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.ibdb.presentation.navigation.Screen


private enum class ListenerState {
    Listening, Received
}

/**
 * Listens to nearby user who want to share a work
 */
@Composable
fun SharedWorkListener(
    viewModel: SharedWorkListenerViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val (state, setState) = remember { mutableStateOf(ListenerState.Listening) }
    val (packet, setPacket) = remember { mutableStateOf(NearbyMsgPacket("empty")) }
    val (connectedEndpointId, setConnectedEndpointId) = remember { mutableStateOf("") }

    DisposableEffect(viewModel.connection) {

        val handler = object : ConnectionLifecycleHandler() {
            // Pause/Restart advertising when discovery is started : We can only have
            // one connection anyway
            override fun onDiscoveryStarted() = viewModel.connection.stopAdvertising()
            override fun onDiscoveryStopped() = viewModel.connection.startAdvertising()

            // Pause/Restart advertising when connected to an endpoint : We can only have
            // one connection anyway
            override fun onConnected(endpointId: String) {
                setConnectedEndpointId(endpointId)
                viewModel.connection.stopAdvertising()
            }

            override fun onDisconnected(endpointId: String) {
                setConnectedEndpointId("")
                setState(ListenerState.Listening)
                viewModel.connection.startAdvertising()
            }

            override fun onPacketReceived(packet: NearbyMsgPacket) {
                setPacket(packet)
                setState(ListenerState.Received)
            }
        }

        // Listen to the connection's events and start advertising
        viewModel.connection.addListener(handler)
        viewModel.connection.startAdvertising()

        onDispose {
            // Stop listening and stop advertising
            viewModel.connection.stopAdvertising()
            // And disconnect if the connection is active
            if (viewModel.connection.isConnected()) {
                viewModel.connection.disconnect()
            }
            viewModel.connection.removeListener(handler)
        }
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