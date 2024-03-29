package com.github.leuludyha.ibdb.presentation.components.sharing

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.Endpoint
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.utils.CenteredLoading
import com.github.leuludyha.ibdb.presentation.components.utils.CenteredText
import com.github.leuludyha.ibdb.presentation.components.utils.ItemList

private enum class SharerState {
    Loading, Discovering, WaitingForConnection, Connected,
    Error,
}

/**
 * Opens a menu to select a user to share a book with
 */
@Composable
fun ShareWorkComponent(
    padding: PaddingValues,
    workId: String,
    onSuccessfullyShared: () -> Unit,
) {
    SharingPermissionRequired {
        ShareInnerWorkComponent(
            padding = padding,
            workId = workId,
            onSuccessfullyShared = onSuccessfullyShared,
        )
    }
}

@Composable
private fun ShareInnerWorkComponent(
    padding: PaddingValues,
    workId: String,
    viewModel: ShareWorkComponentViewModel = hiltViewModel(),
    onSuccessfullyShared: () -> Unit,
) {

    val (error, setError) = remember { mutableStateOf("") }
    val (state, setState) = remember { mutableStateOf(SharerState.Loading) }

    DisposableEffect(viewModel.connection) {
        val handler = object : ConnectionLifecycleHandler() {
            override fun onMount() {
                viewModel.connection.startDiscovery()
            }

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
                Log.i("SHARE_SHARE", "FOUND ENDPOINT")
                viewModel.updateEndpointList()
            }

            // Set error state if the connection is rejected
            override fun onConnectionRejected(endpointId: String) {
                setError("Connection rejected by $endpointId")
                setState(SharerState.Error)
            }

            override fun onConnected(endpoint: Endpoint) {
                setState(SharerState.Connected)
            }

            override fun onDisconnected(endpointId: String) {
                if (state == SharerState.Connected) {
                    setError("Disconnected")
                    setState(SharerState.Error)
                }
            }

            override fun onDismount() {
                if (viewModel.connection.isDiscovering()) {
                    viewModel.connection.stopDiscovery()
                }
                // TODO Careful with race condition with when(state) -> Connected -> disconnect()
                if (viewModel.connection.isConnected()) {
                    viewModel.connection.disconnect()
                }
            }
        }

        // On load, add listener and start discovery
        viewModel.connection.addListener(handler)
        // On dispose, stop discovery and remove listener
        onDispose {
            viewModel.connection.removeListener(handler)
        }
    }

    fun connectTo(endpointId: String) {
        viewModel.connection.requestConnection(endpointId)
        setState(SharerState.WaitingForConnection)
    }

    when (state) {
        // Waiting for discovery to start
        SharerState.Loading -> {
            CenteredLoading(modifier = Modifier.padding(padding))
        }

        // List all users discovered so far
        SharerState.Discovering -> {
            EndpointList(
                viewModel = viewModel,
                connectTo = { connectTo(it) },
                padding = padding
            )
        }

        // Displays a loading screen with "Connecting..." text
        SharerState.WaitingForConnection -> CenteredLoading(
            modifier = Modifier.padding(padding),
            label = stringResource(id = R.string.nearby_connecting)
        )

        // Sends the packet then disconnects
        SharerState.Connected -> {
            if (viewModel.connection.isConnected()) {
                viewModel.connection.sendPacket(NearbyMsgPacket(NearbyMsgPacket.ShareWork, workId))
                onSuccessfullyShared()
            } else {
                setState(SharerState.Error)
                setError("Disconnected")
            }
        }

        // Otherwise, display error text with description
        else -> {
            CenteredText(text = error)
        }
    }
}

@Composable
private fun EndpointList(
    viewModel: ShareWorkComponentViewModel,
    connectTo: (String) -> Unit,
    padding: PaddingValues
) {
    if (viewModel.endpointChoices.isEmpty()) {
        CenteredLoading(
            modifier = Modifier.padding(padding),
            label = stringResource(id = R.string.nearby_discovering_people)
        )
    } else {
        ItemList(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            values = viewModel.endpointChoices,
            orientation = Orientation.Vertical
        ) { endpoint ->
            Button(
                modifier = Modifier.fillMaxWidth(fraction = 0.6f),
                onClick = {
                    connectTo(endpoint.id)
                    if (viewModel.connection.isDiscovering()) {
                        viewModel.connection.stopDiscovery()
                    }
                }
            ) { Text(text = endpoint.name) }
        }
    }
}