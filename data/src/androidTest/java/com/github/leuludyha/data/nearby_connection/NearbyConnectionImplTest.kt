package com.github.leuludyha.data.nearby_connection

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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

    @Before
    fun init() {
        connection = NearbyConnectionTester(
            ApplicationProvider.getApplicationContext(),
            "John Mockentosh",
            client = MockNearbyConnectionClient()
        )
    }

//========== ======== ==== ==
//        DISCOVERY
//========== ======== ==== ==

    @Test
    fun internalStateIsDiscoveryOnDiscoveryStarted() {
        connection.startDiscovery()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Discovering))
    }

    @Test
    fun stoppingDiscoveryWhenNotInDiscoveryModeThrowsException() {
        assertThrows(UnsupportedOperationException::class.java) {
            connection.stopDiscovery()
        }
    }

    @Test
    fun internalStateIsIdleOnDiscoveryStopped() {
        connection.startDiscovery()
        connection.stopDiscovery()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Idle))
    }

//========== ======== ==== ==
//        ADVERTISING
//========== ======== ==== ==

    @Test
    fun internalStateIsAdvertisingOnAdvertisingStarted() {
        connection.startAdvertising()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Advertising))
    }

    @Test
    fun stoppingAdvertisingWhenNotInAdvertisingModeThrowsException() {
        assertThrows(UnsupportedOperationException::class.java) {
            connection.stopAdvertising()
        }
    }

    @Test
    fun internalStateIsIdleOnAdvertisingStopped() {
        connection.startAdvertising()
        connection.stopAdvertising()
        assertThat(connection.internalState, Is(NearbyConnectionImpl.State.Idle))
    }


}