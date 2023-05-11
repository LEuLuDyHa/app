package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.domain.model.library.Work

private enum class State {
    Loading, Discovering, EndpointChoice, WaitingForConnection, ConnectionPrompt, Connected,
    Error,
}

/**
 * Opens a menu to select a user to share a book with
 */
@Composable
fun ShareWorkComponent(
    work: Work,
    viewModel: ShareWorkComponentViewModel = hiltViewModel(),
    onSuccessfullyShared: () -> Unit,
) {

    val (error, setError) = remember { mutableStateOf("") }
    val (state, setState) = remember { mutableStateOf(State.Loading) }

    DisposableEffect(viewModel.connection) {
        val handler = object : ConnectionLifecycleHandler() {
            // On error, display the error in the state
            override fun onError(description: String) {
                setError(description)
                setState(State.Error)
            }

            // On discovery successfully started, display endpoints choices
            override fun onDiscoveryStarted() {
                setState(State.EndpointChoice)
            }

            // Update the list of endpoints available if the list changes
            override fun onFoundEndpointsChanged() {
                viewModel.updateEndpointList()
            }

            // Set error state if the connection is rejected
            override fun onConnectionRejected(endpointId: String) {
                setError("Connection rejected by $endpointId")
                setState(State.Error)
            }

            override fun onConnected(endpointId: String) {
                setState(State.Connected)
            }

            override fun onDisconnected(endpointId: String) {
                if (state == State.Connected) {
                    setError("Disconnected")
                    setState(State.Error)
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



    when (state) {
        // TODO Other screens : List choice etc...
        State.Connected -> {
            viewModel.connection.sendPacket(NearbyMsgPacket(NearbyMsgPacket.ShareWork, work.id))
            viewModel.connection.disconnect()
            onSuccessfullyShared()
        }
        // Otherwise, display error text with description
        // TODO Refine error display
        else -> Text(text = error)
    }
}