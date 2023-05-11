package com.github.leuludyha.data.nearby_connection

import android.content.Context
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyConnection
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy


class NearbyConnectionImpl(
    private val context: Context,
    private val username: String,
) : NearbyConnection {

    private val serviceId = ""

//========== ======== ==== ==
//        LISTENERS
//========== ======== ==== ==

    /** List of LifecycleHandlers to notify whe  appropriate */
    private val handlers = mutableSetOf<ConnectionLifecycleHandler>()

    override fun addListener(handler: ConnectionLifecycleHandler) {
        handlers.add(handler)
    }

    override fun removeListener(handler: ConnectionLifecycleHandler) {
        handlers.remove(handler)
    }

    private fun notifyAll(action: (ConnectionLifecycleHandler) -> Unit) {
        handlers.forEach { action(it) }
    }

//========== ======== ==== ==
//        STATE
//========== ======== ==== ==

    private val client = Nearby.getConnectionsClient(context)

    /** All states of the Connection */
    enum class State { Idle, Advertising, Discovering, Connected }

    private var state: State = State.Idle

    /** The endpoint this connection is connected to or null if it is not connected */
    private var connectedEndpoint: String? = null

    override fun isConnected(): Boolean = state == State.Connected

//========== ======== ==== ==
//        CALLBACKS
//========== ======== ==== ==

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
                // Bytes payloads are sent as a single chunk, so you'll receive a SUCCESS update immediately
                // after the call to onPayloadReceived().
            }

        }

    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
                client.acceptConnection(endpointId, payloadCallback)
            }


            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {
                        // We're connected! Can now start sending and receiving data.
                        state = State.Connected
                        notifyAll { it.onConnected(endpointId) }
                    }

                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                        // The connection was rejected by one or both sides.
                        notifyAll { it.onConnectionRejected(endpointId) }
                    }

                    ConnectionsStatusCodes.STATUS_ERROR -> {
                        // The connection broke before it was able to be accepted.
                        result.status.statusMessage?.let { msg -> notifyAll { it.onError(msg) } }
                    }

                    else -> {
                        // Unknown status code
                        result.status.statusMessage?.let { msg -> notifyAll { it.onError(msg) } }
                    }
                }
            }

            override fun onDisconnected(endpointId: String) {
                // We've been disconnected from this endpoint. No more data can be
                // sent or received.
                state = State.Idle
                connectedEndpoint = null
                notifyAll { it.onDisconnected(endpointId) }
            }
        }

//========== ======== ==== ==
//        ENDPOINT LIST
//========== ======== ==== ==

    private val discoveredEndpointsIds = mutableSetOf<String>()


    override fun getDiscoveredEndpointIds() = buildList { addAll(discoveredEndpointsIds) }

    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                // An endpoint was found.
                // Update discovered endpoints
                discoveredEndpointsIds.add(endpointId)
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

//========== ======== ==== ==
//        CONTROL METHODS
//========== ======== ==== ==


    override fun requestConnection(endpointId: String) {
        if (state == State.Connected) {
            throw UnsupportedOperationException("Cannot handle multiple connections at once")
        }
        // We request a connection to the specified endpoint.
        client.requestConnection(username, endpointId, connectionLifecycleCallback)
    }

    override fun stopAdvertising() {
        if (state != State.Advertising) {
            throw UnsupportedOperationException("The connection is not in advertising mode")
        }
        client.stopAdvertising()
    }

    override fun startAdvertising() {
        if (state != State.Idle) {
            throw UnsupportedOperationException("The connection is already busy")
        }
        val options = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_POINT_TO_POINT)
            .build()

        client.startAdvertising(username, serviceId, connectionLifecycleCallback, options)
            .addOnSuccessListener { notifyAll { it.onAdvertisingStarted() } }
            .addOnFailureListener { notifyAll { it.onError("Unable to start Advertising !\n$it") } }
    }

    override fun stopDiscovery() {
        if (state != State.Discovering) {
            throw UnsupportedOperationException("The connection is not in discovery mode")
        }
    }

    override fun startDiscovery() {
        if (state != State.Idle) {
            throw UnsupportedOperationException("The connection is already busy")
        }
        val options = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_POINT_TO_POINT)
            .build()

        client.startDiscovery(serviceId, endpointDiscoveryCallback, options)
            .addOnSuccessListener { notifyAll { it.onDiscoveryStarted() } }
            .addOnFailureListener { notifyAll { it.onError("Unable to start discovery !\n$it") } }
    }

    override fun sendPacket(packet: NearbyMsgPacket) {
        if (state != State.Connected) {
            throw UnsupportedOperationException("The connection is not connected to an endpoint")
        }
        connectedEndpoint?.let {
            client.sendPayload(
                it, Payload.fromBytes(
                    packet.descriptor.encodeToByteArray()
                )
            )
        } ?: throw IllegalStateException("The endpoint is null")
    }

    override fun disconnect() {
        if (state != State.Connected) {
            throw UnsupportedOperationException("The connection is not connected to an endpoint")
        }
        connectedEndpoint?.let {
            client.disconnectFromEndpoint(it)
            state = State.Idle
        } ?: throw IllegalStateException("The endpoint is null")
    }

//========== ======== ==== ==
//        MESSAGE HANDLING
//========== ======== ==== ==

    /** Receive the message */
    private fun receive(byteMsg: ByteArray) {
        val packet = NearbyMsgPacket(byteMsg.decodeToString())

        if (!packet.isValid()) {
            throw IllegalArgumentException("Packet header is not valid : $packet")
        }

        // On valid packet, notify all listeners
        notifyAll { it.onPacketReceived(packet); }
    }

}
