package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket

/**
 * Listens to nearby user who want to share a work
 */
@Composable
fun SharedWorkListener(
    viewModel: SharedWorkListenerViewModel = hiltViewModel()
) {

    DisposableEffect(viewModel.connection) {

        val handler = object : ConnectionLifecycleHandler() {
            // Pause/Restart advertising when discovery is started : We can only have
            // one connection anyway
            override fun onDiscoveryStarted() = viewModel.connection.stopAdvertising()
            override fun onDiscoveryStopped() = viewModel.connection.startAdvertising()

            // Pause/Restart advertising when connected to an endpoint : We can only have
            // one connection anyway
            override fun onConnected(endpointId: String) = viewModel.connection.stopAdvertising()
            override fun onDisconnected(endpointId: String) =
                viewModel.connection.startAdvertising()

            override fun onPacketReceived(packet: NearbyMsgPacket) {
                // TODO Handle packet reception
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
}