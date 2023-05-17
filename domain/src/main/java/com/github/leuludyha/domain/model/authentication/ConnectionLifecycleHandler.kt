package com.github.leuludyha.domain.model.authentication

/**
 * Implement methods of this class with lambdas and add this listener to [NearbyConnection]
 */
abstract class ConnectionLifecycleHandler {

    /** Called when an error occurred, connection state is reset in this case */
    open fun onError(description: String) {}

    /** Called when the list of endpoints discovered changed */
    open fun onFoundEndpointsChanged() {}

    /** Called when a packet is received from an endpoint */
    open fun onPacketReceived(packet: NearbyMsgPacket) {}

//========== ======== ==== ==
//        CONNECTION
//========== ======== ==== ==

    /** Called when this device connected to another endpoint */
    open fun onConnected(endpointId: String) {}

    /** Called when the connection was refused by one of the endpoints */
    open fun onConnectionRejected(endpointId: String) {}


    /** Called when the connection was disconnected from the specified endpoint */
    open fun onDisconnected(endpointId: String) {}

//========== ======== ==== ==
//        DISCOVERY
//========== ======== ==== ==

    /** Called when the connection started discovery */
    open fun onDiscoveryStarted() {}

    /** Called when the connection stopped discovery */
    open fun onDiscoveryStopped() {}

//========== ======== ==== ==
//        ADVERTISEMENT
//========== ======== ==== ==

    /** Called when the connection started advertising */
    open fun onAdvertisingStarted() {}

    /** Called when the connection stopped advertising */
    open fun onAdvertisingStopped() {}

//========== ======== ==== ==
//        ONE SHOTS
//========== ======== ==== ==

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