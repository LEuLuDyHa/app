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
    enum class State { Idle, Advertising, Discovering, Connected }

    protected var state: State = State.Idle

    /** The endpoint this connection is connected to or null if it is not connected */
    private var connectedEndpoint: Endpoint? = null

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
                connectedEndpoint = Endpoint(
                    name = info.endpointName,
                    id = endpointId
                )
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
                        putToRest()
                        notifyAll { it.onConnectionRejected(endpointId) }
                    }

                    ConnectionsStatusCodes.STATUS_ERROR -> {
                        // The connection broke before it was able to be accepted.
                        putToRest()
                        result.status.statusMessage?.let { msg -> notifyAll { it.onError(msg) } }
                    }

                    else -> {
                        // Unknown status code
                        result.status.statusMessage?.let { msg ->
                            notifyAll { it.onError(msg) }
                        }
                    }
                }
            }

            override fun onDisconnected(endpointId: String) {
                // We've been disconnected from this endpoint. No more data can be
                // sent or received.
                Log.i("DISCONNECTED", "DISCONNECTED")
                putToRest()
                connectedEndpoint = null
                notifyAll { it.onDisconnected(endpointId) }
            }
        }

//========== ======== ==== ==
//        ENDPOINT LIST
//========== ======== ==== ==

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

//========== ======== ==== ==
//        CONTROL METHODS
//========== ======== ==== ==

    override fun requestConnection(endpointId: String) {
        if (state == State.Connected) {
            throw UnsupportedOperationException("Cannot handle multiple connections at once")
        }
        when (state) {
            State.Advertising -> stopAdvertising()
            State.Discovering -> stopDiscovery()
            else -> {}
        }
        // We request a connection to the specified endpoint.
        client.requestConnection(username, endpointId, connectionLifecycleCallback)
    }

    override fun stopAdvertising() {
        if (state != State.Advertising) {
            throw UnsupportedOperationException("The connection is not in advertising mode")
        }
        client.stopAdvertising()
        state = State.Idle
    }

    override fun isAdvertising() = state == State.Advertising

    override fun startAdvertising() {
        if (state != State.Idle) {
            Log.i("SHARE", "ERROR CONNECTION BUSY")
            throw UnsupportedOperationException("The connection is already busy")
        }

        val options = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_POINT_TO_POINT)
            .build()
        state = State.Advertising

        client.startAdvertising(username, serviceId, connectionLifecycleCallback, options)
            .addOnSuccessListener {
                Log.i("SHARE", "Advertising successfully started")
                notifyAll { it.onAdvertisingStarted() }
            }
            .addOnFailureListener { error ->
                state = State.Idle
                Log.i("SHARE", error.message!!)
                putToRest()
                notifyAll {
                    it.onError(
                        "Unable to start advertising !" +
                                "\n${error.message}" +
                                "\n${error.stackTrace.joinToString(separator = "\n")}"
                    )
                }
            }
    }

    private fun putToRest() {
        if (isConnected()) {
            disconnect()
        }
        if (isAdvertising()) {
            stopAdvertising()
        }
        if (isDiscovering()) {
            stopDiscovery()
        }
        state = State.Idle
        connectedEndpoint = null
    }

    override fun stopDiscovery() {
        if (state != State.Discovering) {
            throw UnsupportedOperationException("The connection is not in discovery mode")
        }
        client.stopDiscovery()
        state = State.Idle
    }

    override fun isDiscovering() = state == State.Discovering

    override fun startDiscovery() {
        val options = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_POINT_TO_POINT)
            .build()

        state = State.Discovering
        client.startDiscovery(serviceId, endpointDiscoveryCallback, options)
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
        } ?: throw IllegalStateException("The endpoint is null")
    }

    override fun disconnect() {
        if (state != State.Connected) {
            throw UnsupportedOperationException("The connection is not connected to an endpoint")
        }
        connectedEndpoint?.let {
            client.disconnectFromEndpoint(it.id)
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