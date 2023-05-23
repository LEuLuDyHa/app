package com.github.leuludyha.data.nearby_connection

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.google.common.truth.Truth.assertThat
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.`is` as Is

@RunWith(AndroidJUnit4::class)
class NearbyConnectionImplTest {

    private lateinit var connection: NearbyConnectionTester
    private lateinit var failConnection: NearbyConnectionTester

    class MockConnectionLifecycleHandler: ConnectionLifecycleHandler() { }

    @Before
    fun init() {
        connection = NearbyConnectionTester(
            ApplicationProvider.getApplicationContext(),
            "John Mockentosh",
            client = MockNearbyConnectionClient()
        )

        failConnection = NearbyConnectionTester(
            ApplicationProvider.getApplicationContext(),
            "Username",
            client = MockFailingNearbyConnectionClient()
        )
    }

//========== ======== ==== ==
//        DISCOVERY
//========== ======== ==== ==

    @Test
    fun internalStateIsDiscoveryOnDiscoveryStarted() {
        connection.startDiscovery()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Discovering))
        assertThat(connection.isDiscovering()).isTrue()
    }

    @Test
    fun internalStateIsIdleOnDiscoveryFailed() {
        failConnection.startDiscovery()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Idle))
        assertThat(connection.isDiscovering()).isFalse()
    }

    @Test
    fun internalStateIsIdleOnDiscoveryStopped() {
        connection.stopDiscovery()
        connection.startDiscovery()
        connection.stopDiscovery()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Idle))
        assertThat(connection.isDiscovering()).isFalse()
    }

//========== ======== ==== ==
//        ADVERTISING
//========== ======== ==== ==

    @Test
    fun internalStateIsAdvertisingOnAdvertisingStarted() {
        connection.startAdvertising()
        connection.startAdvertising()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Advertising))
        assertThat(connection.isAdvertising()).isTrue()
    }

    @Test
    fun internalStateIsIdleOnAdvertisingFailed() {
        failConnection.startAdvertising()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Idle))
        assertThat(connection.isAdvertising()).isFalse()
    }

    @Test
    fun internalStateIsIdleOnAdvertisingStopped() {
        connection.stopAdvertising()
        connection.startAdvertising()
        connection.stopAdvertising()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Idle))
        assertThat(connection.isAdvertising()).isFalse()
    }

    @Test
    fun addListenerDoesNotCrash() {
        connection.addListener(MockConnectionLifecycleHandler())
        assertThat(true).isTrue()
    }

    @Test
    fun removeListenerOnNonPresentElementDoesNotCrash() {
        connection.removeListener(MockConnectionLifecycleHandler())
        assertThat(true).isTrue()
    }

    @Test
    fun removeListenerOnPresentElementDoesNotCrash() {
        connection.addListener(MockConnectionLifecycleHandler())
        connection.removeListener(MockConnectionLifecycleHandler())
        assertThat(true).isTrue()
    }

    @Test
    fun getDiscoveredEndpointIdsIsEmpty() {
        assertThat(connection.getDiscoveredEndpointIds()).isEmpty()
    }

    @Test
    fun isConnectedReturnsFalse() {
        assertThat(connection.isConnected()).isFalse()
    }

    @Test
    fun requestConnectionThrowsIllegalStateExceptionOnNotDiscoveringState() {
        assertThrows(java.lang.IllegalStateException::class.java) {
            connection.requestConnection("id")
        }
    }

    @Test
    fun disconnectDoesNotFailOnStateNotConnected() {
        connection.disconnect()
        assertThat(true).isTrue()
    }

    @Test
    fun sendPacketThrowsUnsupportedOperationExceptionOnNotConnectedState() {
        assertThrows(java.lang.UnsupportedOperationException::class.java) {
            connection.sendPacket(NearbyMsgPacket(""))
        }
    }
}