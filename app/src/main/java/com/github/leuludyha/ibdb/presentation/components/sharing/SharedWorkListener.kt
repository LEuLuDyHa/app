package com.github.leuludyha.ibdb.presentation.components.sharing

import android.R
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.Endpoint
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
    navController: NavHostController,
    viewModel: SharedWorkListenerViewModel = hiltViewModel()
) {
    SharingPermissionRequired {
        SharedWorkListenerComponent(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
private fun SharedWorkListenerComponent(
    viewModel: SharedWorkListenerViewModel,
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
                viewModel = viewModel,
                navController = navController,
                onProcessed = {
                    if (viewModel.connection.isConnected()) {
                        viewModel.connection.disconnect()
                    }
                    setState(ListenerState.Listening)
                }
            )
        }


        else -> {
            Box(
                modifier = Modifier
                    .testTag("shared_work_listener::loading_box")
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { SpinningProgressBar() }
        }
    }
}

@Composable
private fun PacketProcessor(
    endpoint: Endpoint,
    packet: NearbyMsgPacket,
    viewModel: SharedWorkListenerViewModel,
    navController: NavHostController,
    onProcessed: () -> Unit
) {
    when (packet.prefix) {
        NearbyMsgPacket.ShareWork.id -> {
            ProcessShareWorkPacket(
                endpoint = endpoint,
                workJson = packet.content,
                viewModel = viewModel,
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
    workJson: String,
    viewModel: SharedWorkListenerViewModel,
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
                viewModel.saveWorkFromJson(workJson) {
                    navController.navigate(Screen.BookDetails.passBookId(workJson))
                    onProcessed()
                }
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
        icon = { R.drawable.ic_dialog_alert }
    )
}


/**
 * An animated Spinning Progress Bar.
 */
@Composable
private fun SpinningProgressBar(modifier: Modifier = Modifier) {
    val count = 12

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(count * 80, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.size(48.dp)) {

        val canvasWidth = size.width
        val canvasHeight = size.height

        val width = size.width * .3f
        val height = size.height / 8

        val cornerRadius = width.coerceAtMost(height) / 2

        for (i in 0..360 step 360 / count) {
            rotate(i.toFloat()) {
                drawRoundRect(
                    color = Color.LightGray.copy(alpha = .7f),
                    topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                    size = Size(width, height),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }

        val coefficient = 360f / count

        for (i in 1..4) {
            rotate((angle.toInt() + i) * coefficient) {
                drawRoundRect(
                    color = Color.Gray.copy(alpha = (0.2f + 0.2f * i).coerceIn(0f, 1f)),
                    topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2),
                    size = Size(width, height),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
        }
    }
}

