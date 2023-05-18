package com.github.leuludyha.ibdb

import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.Endpoint
import com.github.leuludyha.domain.model.authentication.NearbyConnection
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket

class MockNearbyConnection : NearbyConnection {
    var advertising: Boolean = false
    var discovering: Boolean = false
    var connected: Boolean = false

    override fun startAdvertising() {
        advertising = true
    }

    override fun stopDiscovery() {
        discovering = false
    }

    override fun isDiscovering(): Boolean {
        return discovering
    }

    override fun startDiscovery() {
        discovering = true
    }

    override fun stopAdvertising() {
        advertising = false
    }

    override fun isAdvertising(): Boolean {
        return advertising
    }

    override fun requestConnection(endpointId: String) {
        connected = true
    }

    override fun getDiscoveredEndpointIds(): List<Endpoint> {
        return listOf()
    }

    override fun sendPacket(packet: NearbyMsgPacket) {
        //Nothing
    }

    override fun disconnect() {
        connected = false
    }

    override fun addListener(handler: ConnectionLifecycleHandler) {
        //TODO: Add a few tests here if wanted
    }

    override fun removeListener(handler: ConnectionLifecycleHandler) {
        //Nothing
    }

    override fun isConnected(): Boolean {
        return connected
    }

}