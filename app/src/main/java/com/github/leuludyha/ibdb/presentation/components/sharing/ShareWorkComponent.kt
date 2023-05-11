package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.ibdb.presentation.components.ItemList

private enum class SharerState {
    Loading, Discovering, WaitingForConnection, Connected,
    Error,
}

/**
 * Opens a menu to select a user to share a book with
 */
@Composable
fun ShareWorkComponent(
    navController: NavHostController,
    padding: PaddingValues,
    workId: String,
    viewModel: ShareWorkComponentViewModel = hiltViewModel(),
    onSuccessfullyShared: () -> Unit,
) {

    val (error, setError) = remember { mutableStateOf("") }
    val (state, setState) = remember { mutableStateOf(SharerState.Loading) }

    DisposableEffect(viewModel.connection) {
        val handler = object : ConnectionLifecycleHandler() {
            // On error, display the error in the state
            override fun onError(description: String) {
                setError(description)
                setState(SharerState.Error)
            }

            // On discovery successfully started, display endpoints choices
            override fun onDiscoveryStarted() {
                setState(SharerState.Discovering)
            }

            // Update the list of endpoints available if the list changes
            override fun onFoundEndpointsChanged() {
                viewModel.updateEndpointList()
            }

            // Set error state if the connection is rejected
            override fun onConnectionRejected(endpointId: String) {
                setError("Connection rejected by $endpointId")
                setState(SharerState.Error)
            }

            override fun onConnected(endpointId: String) {
                setState(SharerState.Connected)
            }

            override fun onDisconnected(endpointId: String) {
                if (state == SharerState.Connected) {
                    setError("Disconnected")
                    setState(SharerState.Error)
                }
            }
        }

        // On load, add listener and start discovery
        viewModel.connection.addListener(handler)
        viewModel.connection.startDiscovery()
        // On dispose, stop discovery and remove listener
        onDispose {
            viewModel.connection.stopDiscovery()
            // TODO Careful with race condition with when(state) -> Connected -> disconnect()
            if (viewModel.connection.isConnected()) {
                viewModel.connection.disconnect()
            }
            viewModel.connection.removeListener(handler)
        }
    }

    fun connectTo(endpointId: String) {
        viewModel.connection.requestConnection(endpointId)
        setState(SharerState.WaitingForConnection)
    }

    when (state) {
        SharerState.Loading -> Loading()
        // TODO Other screens : List choice etc...
        SharerState.Discovering -> {
            // Start discovery mode
            DisposableEffect(viewModel.connection) {
                viewModel.connection.startDiscovery()
                onDispose { viewModel.connection.stopDiscovery() }
            }
            // List all users discovered so far
            ItemList(values = viewModel.endpointChoices) { endpoint ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { connectTo(endpoint.id) }
                    ) { Text(text = endpoint.id) }
                }
            }
        }

        SharerState.WaitingForConnection -> Loading()

        SharerState.Connected -> {
            viewModel.connection.sendPacket(NearbyMsgPacket(NearbyMsgPacket.ShareWork, workId))
            viewModel.connection.disconnect()
            onSuccessfullyShared()
        }
        // Otherwise, display error text with description
        // TODO Refine error display
        else -> Text(text = error)
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { CircularProgressIndicator() }
}