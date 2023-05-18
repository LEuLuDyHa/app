package com.github.leuludyha.data.nearby_connection

import android.content.Context
import android.util.Log
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.Endpoint
import com.github.leuludyha.domain.model.authentication.NearbyConnection
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy

// //=============================================================================================\\
// ||                                                                                             ||
// ||                                           BEGIN                                             ||
// ||                                                                                             ||
// \\=============================================================================================//

/**
 * Uses a state machine
 * @throws IllegalStateException When the state machine has the incorrect state. Used for
 * debugging since it should not happen
 */
open class NearbyConnectionImpl(
    private val context: Context,
    private val username: String,
    private val client: ConnectionsClient = Nearby.getConnectionsClient(context)
) : NearbyConnection {

    private val serviceId = "com.google.android.gms.nearby.connection.SERVICE_ID"

//========== ======== ==== ==
//        LISTENERS
//========== ======== ==== ==

    /** List of LifecycleHandlers to notify whe  appropriate */
    private val handlers = mutableSetOf<ConnectionLifecycleHandler>()

    override fun addListener(handler: ConnectionLifecycleHandler) {
        handlers.add(handler)
        handler.onMount()
    }

    override fun removeListener(handler: ConnectionLifecycleHandler) {
        handlers.remove(handler)
        handler.onDismount()
    }

    private fun notifyAll(action: (ConnectionLifecycleHandler) -> Unit) {
        handlers.forEach { action(it) }
    }

//========== ======== ==== ==
//        STATE
//========== ======== ==== ==

    /** All states of the Connection */
    enum class State { Idle, Advertising, Discovering, ConnectionPending, Connected }

    private var pState: State = State.Idle

    /** The current state of the connection */
    protected var state
        set(value) {
            Log.w("STATE", "State changed to $value", Error())
            pState = value
        }
        get() = pState

    /** The endpoint this connection is connected to or null if it is not connected */
    private var connectedEndpoint: Endpoint? = null

// //=============================================================================================\\
// ||                                                                                             ||
// ||                                         CALLBACKS                                           ||
// ||                                                                                             ||
// \\=============================================================================================//

    private val payloadCallback: PayloadCallback =
        object : PayloadCallback() {
            override fun onPayloadReceived(endpointId: String, payload: Payload) {
                // This always gets the full data of the payload. Is null if it's not a BYTES payload.
                if (payload.type == Payload.Type.BYTES) {
                    // Call callback
                    receive(payload.asBytes()!!)
                } else {
                    throw UnsupportedOperationException("The payload type ${payload.type} is not supported !")
                }
            }

            override fun onPayloadTransferUpdate(
                endpointId: String,
                update: PayloadTransferUpdate
            ) {
                // Bytes payloads are sent as a single chunk, so you'll receive a
                // SUCCESS update immediately after the call to onPayloadReceived().
            }

        }

    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            //========== ======== ==== ==
            //        INITIATED
            //========== ======== ==== ==
            override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
                when (state) {
                    State.Advertising -> {
                        // First stop advertising since someone requested connection
                        stopAdvertising()
                        // Then Update state
                        updateStateTo(State.ConnectionPending)
                    }

                    State.ConnectionPending -> { /* All good */
                    }
                    // Discovery should have stopped on requestConnection
                    else -> throw IllegalStateException(
                        "Something went wrong..." +
                                "\nState is ${state}, Expected Pending or Advertising"
                    )
                }

                connectedEndpoint = Endpoint(
                    name = info.endpointName,
                    id = endpointId
                )
                client.acceptConnection(endpointId, payloadCallback)
            }

            //========== ======== ==== ==
            //        RESOLVED
            //========== ======== ==== ==
            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {
                        checkThatStateIs(State.ConnectionPending)
                        // We're connected! Can now start sending and receiving data.
                        updateStateTo(State.Connected)
                        // At this point, both side should have connectedEnpoint not null
                        notifyAll { it.onConnected(connectedEndpoint!!) }
                    }

                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                        // The connection was rejected by one or both sides.
                        reset()
                        notifyAll { it.onConnectionRejected(endpointId) }
                    }

                    ConnectionsStatusCodes.STATUS_ERROR -> {
                        // The connection broke before it was able to be accepted.
                        reset()
                        result.status.statusMessage?.let { msg -> notifyAll { it.onError(msg) } }
                    }

                    else -> {
                        reset()
                        // Unknown status code
                        result.status.statusMessage?.let { msg ->
                            notifyAll { it.onError(msg) }
                        }
                    }
                }
            }

            //========== ======== ==== ==
            //        DISCONNECTED
            //========== ======== ==== ==
            override fun onDisconnected(endpointId: String) {
                // We've been disconnected from this endpoint. No more data can be
                // sent or received.
                reset()
                notifyAll { it.onDisconnected(endpointId) }
            }
        }

// //=============================================================================================\\
// ||                                                                                             ||
// ||                                     ENDPOINTS LISTING                                       ||
// ||                                                                                             ||
// \\=============================================================================================//

    private val discoveredEndpointsIds = mutableMapOf<String, Endpoint>()

    override fun getDiscoveredEndpointIds() = buildList { addAll(discoveredEndpointsIds.values) }

    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                // An endpoint was found.
                // Update discovered endpoints
                discoveredEndpointsIds[endpointId] = Endpoint(
                    name = info.endpointName,
                    id = endpointId
                )
                // And notify lifecycle handler
                notifyAll { it.onFoundEndpointsChanged() }
            }

            override fun onEndpointLost(endpointId: String) {
                // A previously discovered endpoint has gone away.
                // Update discovered endpoints
                discoveredEndpointsIds.remove(endpointId)
                // And notify lifecycle handler
                notifyAll { it.onFoundEndpointsChanged() }
            }
        }

// //=============================================================================================\\
// ||                                                                                             ||
// ||                                      CONTROL METHODS                                        ||
// ||                                                                                             ||
// \\=============================================================================================//

    private fun reset() {
        disconnect()
        stopAdvertising()
        stopDiscovery()
        // Clear all
        connectedEndpoint = null
        discoveredEndpointsIds.clear()
        // Update state
        updateStateTo(State.Idle)
    }

//========== ======== ==== ==
//        CONNECTION
//========== ======== ==== ==

    override fun requestConnection(endpointId: String) {
        if (state == State.Connected) {
            // Cannot handle multiple connections at once
            return
        }

        when (state) {
            State.Discovering -> {
                // First stop discovery
                stopDiscovery()
                // We request a connection to the specified endpoint.
                updateStateTo(State.ConnectionPending)
                client.requestConnection(username, endpointId, connectionLifecycleCallback)
            }
            // Trying to connect when only discovering or in other state
            else -> throw IllegalStateException("Something went wrong...")
        }
    }

    override fun isConnected(): Boolean = state == State.Connected

    override fun disconnect() {
        if (state != State.Connected) {
            return
        }

        updateStateTo(State.Idle)
        connectedEndpoint?.let {
            client.disconnectFromEndpoint(it.id)
            // Back to idle default mode
            reset()
        } ?: throw IllegalStateException("The endpoint is null")
    }

//========== ======== ==== ==
//        ADVERTISEMENT
//========== ======== ==== ==

    override fun stopAdvertising() {
        if (!isAdvertising()) {
            return
        }

        client.stopAdvertising()
        updateStateTo(State.Idle)
    }

    override fun isAdvertising() = state == State.Advertising

    private val advertisingOptions = AdvertisingOptions.Builder()
        .setStrategy(Strategy.P2P_POINT_TO_POINT)
        .build()

    override fun startAdvertising() {
        if (state != State.Idle) {
            // Trying to start advertising but the connection is busy...
            return
        }

        updateStateTo(State.Advertising)

        client.startAdvertising(
            username,
            serviceId,
            connectionLifecycleCallback,
            advertisingOptions
        )
            .addOnSuccessListener {
                Log.i("SHARE", "Advertising successfully started")
                notifyAll { it.onAdvertisingStarted() }
            }
            .addOnFailureListener { error ->
                state = State.Idle
                Log.i("SHARE", error.message!!)
                reset()
                notifyAll {
                    it.onError(
                        "Unable to start advertising !" +
                                "\n${error.message}" +
                                "\n${error.stackTrace.joinToString(separator = "\n")}"
                    )
                }
            }
    }

//========== ======== ==== ==
//        DISCOVERY
//========== ======== ==== ==

    override fun stopDiscovery() {
        if (!isDiscovering()) {
            return
        }

        client.stopDiscovery()
        // Update internal state
        updateStateTo(State.Idle)
        // Clear endpoints found since we stopped discovering
        discoveredEndpointsIds.clear()
        notifyAll { it.onFoundEndpointsChanged() }
    }

    override fun isDiscovering() = state == State.Discovering

    private val discoveryOptions = DiscoveryOptions.Builder()
        .setStrategy(Strategy.P2P_POINT_TO_POINT)
        .build()

    override fun startDiscovery() {
        if (state != State.Idle) {
            // Trying to start discovering but the connection is busy...
            return
        }

        // Update state
        updateStateTo(State.Discovering)

        client.startDiscovery(serviceId, endpointDiscoveryCallback, discoveryOptions)
            .addOnSuccessListener { notifyAll { it.onDiscoveryStarted() } }
            .addOnFailureListener { error ->
                state = State.Idle
                notifyAll {
                    it.onError(
                        "Unable to start discovery !" +
                                "\n${error.message}" +
                                "\n${error.stackTrace.joinToString(separator = "\n")}"
                    )
                }
            }
    }

//========== ======== ==== ==
//        MESSAGING
//========== ======== ==== ==

    override fun sendPacket(packet: NearbyMsgPacket) {
        if (state != State.Connected) {
            throw UnsupportedOperationException("The connection is not connected to an endpoint")
        }

        connectedEndpoint?.let {
            client.sendPayload(
                it.id, Payload.fromBytes(
                    packet.descriptor.encodeToByteArray()
                )
            )
        } ?: throw IllegalStateException(
            "The endpoint this connection is supposed to be connected to is null"
        )
    }

    /** Receive the message */
    private fun receive(byteMsg: ByteArray) {
        val packet = NearbyMsgPacket(byteMsg.decodeToString())

        if (!packet.isValid()) {
            throw IllegalArgumentException("Packet header is not valid : $packet")
        }

        // On valid packet, notify all listeners
        notifyAll { it.onPacketReceived(packet); }
    }

//========== ======== ==== ==
//        UTILS
//========== ======== ==== ==

    private fun updateStateTo(nextState: State) {
        this.state = nextState
    }

    /**
     * Check that the current state is the specified state
     * @throws IllegalStateException If [state] != [saneState]
     */
    private fun checkThatStateIs(saneState: State) {
        if (this.state != saneState) {
            throw IllegalStateException(
                "Something went wrong : " +
                        "\nState is ${this.state} when it should be $saneState"
            )
        }
    }
}

// //=============================================================================================\\
// ||                                                                                             ||
// ||                                            END                                              ||
// ||                                                                                             ||
// \\=============================================================================================//