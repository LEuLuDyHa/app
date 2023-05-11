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
}