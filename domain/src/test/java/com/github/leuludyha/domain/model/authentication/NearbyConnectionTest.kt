package com.github.leuludyha.domain.model.authentication

import com.google.common.truth.Truth
import org.junit.Assert.assertThrows
import org.junit.Test

class NearbyConnectionTest {
    @Test
    fun startAdvertisingThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.startAdvertising()
        }
    }

    @Test
    fun stopDiscoveryThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.stopDiscovery()
        }
    }

    @Test
    fun isDiscoveringThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.isDiscovering()
        }
    }

    @Test
    fun startDiscoveryThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.startDiscovery()
        }
    }

    @Test
    fun stopAdvertisingThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.stopAdvertising()
        }
    }

    @Test
    fun isAdvertisingThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.isAdvertising()
        }
    }

    @Test
    fun requestConnectionThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.requestConnection("")
        }
    }

    @Test
    fun getDiscoveredEndpointIdsThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.getDiscoveredEndpointIds()
        }
    }

    @Test
    fun sendPacketThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.sendPacket(NearbyMsgPacket(""))
        }
    }

    @Test
    fun disconnectThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.disconnect()
        }
    }

    @Test
    fun addListenerThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.addListener(MockConnectionLifeCycleHandler())
        }
    }

    @Test
    fun removeListenerThrowsUnsupportedOperationExceptionOnEmpty() {
        assertThrows(UnsupportedOperationException::class.java) {
            NearbyConnection.Empty.removeListener(MockConnectionLifeCycleHandler())
        }
    }

    @Test
    fun isConnectedIsFalse() {
        Truth.assertThat(NearbyConnection.Empty.isConnected()).isFalse()
    }
}

class MockConnectionLifeCycleHandler: ConnectionLifecycleHandler(){ }