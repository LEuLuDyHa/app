package com.github.leuludyha.domain.model.authentication

/**
 * Implement methods of this class with lambdas and add this listener to [NearbyConnection]
 */
abstract class ConnectionLifecycleHandler {

    open fun onError(description: String) {}

    open fun onConnectionRejected(endpointId: String) {}

    open fun onConnected(endpointId: String) {}

    open fun onFoundEndpointsChanged() {}

    open fun onPacketReceived(packet: NearbyMsgPacket) {}

    open fun onDisconnected(endpointId: String) {}

    open fun onDiscoveryStarted() {}

    open fun onAdvertisingStarted() {}

    open fun onDiscoveryStopped() {}

    open fun onAdvertisingStopped() {}

    /**
     * When the listener is successfully listening to the connection for the first time, this
     * method is called
     */
    open fun onMount() {}

    /**
     * When the listener is no longer listening to the connection, this
     * method is called
     */
    open fun onDismount() {}

}