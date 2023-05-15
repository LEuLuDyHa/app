package com.github.leuludyha.data.nearby_connection

import com.google.android.gms.common.api.internal.ApiKey
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionOptions
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.ConnectionsOptions
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.tasks.Task

class MockNearbyConnectionClient : ConnectionsClient {
    override fun getApiKey(): ApiKey<ConnectionsOptions> {
        throw IllegalStateException()
    }

    override fun acceptConnection(p0: String, p1: PayloadCallback): Task<Void> = MockTask()

    override fun cancelPayload(p0: Long): Task<Void> = MockTask()

    override fun rejectConnection(p0: String): Task<Void> = MockTask()

    override fun requestConnection(
        p0: String,
        p1: String,
        p2: ConnectionLifecycleCallback
    ): Task<Void> = MockTask()

    override fun requestConnection(
        p0: ByteArray,
        p1: String,
        p2: ConnectionLifecycleCallback
    ): Task<Void> = MockTask()

    override fun requestConnection(
        p0: String,
        p1: String,
        p2: ConnectionLifecycleCallback,
        p3: ConnectionOptions
    ): Task<Void> = MockTask()

    override fun requestConnection(
        p0: ByteArray,
        p1: String,
        p2: ConnectionLifecycleCallback,
        p3: ConnectionOptions
    ): Task<Void> = MockTask()

    override fun sendPayload(p0: String, p1: Payload): Task<Void> = MockTask()

    override fun sendPayload(p0: MutableList<String>, p1: Payload): Task<Void> = MockTask()

    override fun startAdvertising(
        p0: String,
        p1: String,
        p2: ConnectionLifecycleCallback,
        p3: AdvertisingOptions
    ): Task<Void> = MockTask()

    override fun startAdvertising(
        p0: ByteArray,
        p1: String,
        p2: ConnectionLifecycleCallback,
        p3: AdvertisingOptions
    ): Task<Void> = MockTask()

    override fun startDiscovery(
        p0: String,
        p1: EndpointDiscoveryCallback,
        p2: DiscoveryOptions
    ): Task<Void> = MockTask()

    override fun disconnectFromEndpoint(p0: String) {

    }

    override fun stopAdvertising() {

    }

    override fun stopAllEndpoints() {

    }

    override fun stopDiscovery() {

    }
}