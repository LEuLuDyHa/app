package com.github.leuludyha.domain.model.authentication

interface NearbyConnection {

//========== ======== ==== ==
//        ADVERTISEMENT
//========== ======== ==== ==

    /** Start advertising this device is available for nearby connections */
    fun startAdvertising()

    /** Stop advertising this device for nearby connection */
    fun stopAdvertising()

    /** Whether the connection is in advertising mode */
    fun isAdvertising(): Boolean

//========== ======== ==== ==
//        DISCOVERY
//========== ======== ==== ==

    /** Stop the devices discovery */
    fun stopDiscovery()

    /** Whether the connection is in discovery mode */
    fun isDiscovering(): Boolean

    /** Start the search for nearby devices to connect to */
    fun startDiscovery()

//========== ======== ==== ==
//        CONTROLS
//========== ======== ==== ==

    /**
     * Request a connection to the specified [endpointId].
     * Throws [UnsupportedOperationException] if the [NearbyConnection]
     * object is already connected
     */
    fun requestConnection(endpointId: String)

    /** @return The list of endpoint ids discovered at this point */
    fun getDiscoveredEndpointIds(): List<Endpoint>

    /** Send the bytes to the connection when the connection is established */
    fun sendPacket(packet: NearbyMsgPacket)

    /** Disconnects the [NearbyConnection] if it is already connected */
    fun disconnect()

    /**
     * @return True if the [NearbyConnection] is currently connected to an endpoint, False
     * otherwise
     */
    fun isConnected(): Boolean

//========== ======== ==== ==
//        LISTENERS
//========== ======== ==== ==

    /**
     * Add a [ConnectionLifecycleHandler] to this connection so that it receives callback
     * when appropriate
     */
    fun addListener(handler: ConnectionLifecycleHandler)

    /**
     * Remove the [ConnectionLifecycleHandler] so that it does no longer receive the
     * callback events
     */
    fun removeListener(handler: ConnectionLifecycleHandler)

// //=============================================================================================\\
// ||                                                                                             ||
// ||                                      EMPTY CONNECTION                                       ||
// ||                                                                                             ||
// \\=============================================================================================//

    object Empty : NearbyConnection {
        private fun error() {
            throw UnsupportedOperationException("The connection is empty")
        }

        override fun startAdvertising() = this.error()

        override fun stopDiscovery() = this.error()

        override fun isDiscovering(): Boolean {
            this.error()
            return false
        }

        override fun startDiscovery() = this.error()

        override fun stopAdvertising() = this.error()

        override fun isAdvertising(): Boolean {
            this.error()
            return false
        }

        override fun requestConnection(endpointId: String) = this.error()

        override fun getDiscoveredEndpointIds(): List<Endpoint> {
            this.error()
            return emptyList()
        }

        override fun sendPacket(packet: NearbyMsgPacket) = this.error()

        override fun disconnect() = this.error()

        override fun addListener(handler: ConnectionLifecycleHandler) = this.error()

        override fun removeListener(handler: ConnectionLifecycleHandler) = this.error()

        override fun isConnected(): Boolean = false

    }


}
