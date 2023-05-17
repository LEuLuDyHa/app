package com.github.leuludyha.ibdb.presentation.components.sharing

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.ibdb.R
import com.github.leuludyha.ibdb.presentation.components.utils.ItemList
import com.github.leuludyha.ibdb.presentation.components.utils.Loading

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

            override fun onConnected(endpointId: String) {
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
        SharerState.Loading -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Loading() }

        SharerState.Discovering -> {
            // List all users discovered so far
            if (viewModel.endpointChoices.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = stringResource(id = R.string.nearby_discovering_people)
                    )
                    Loading()
                }
            } else {
                ItemList(
                    modifier = Modifier.padding(padding),
                    values = viewModel.endpointChoices
                ) { endpoint ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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
        }

        SharerState.WaitingForConnection -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(id = R.string.nearby_connecting)
            )
            Loading()
        }

        SharerState.Connected -> {
            if (viewModel.connection.isConnected()) {
                viewModel.connection.sendPacket(NearbyMsgPacket(NearbyMsgPacket.ShareWork, workId))
                viewModel.connection.disconnect()
                onSuccessfullyShared()
            } else {
                setState(SharerState.Error)
                setError("Disconnected")
            }
        }
        // Otherwise, display error text with description
        // TODO Refine error display
        else -> Text(text = error)
    }
}