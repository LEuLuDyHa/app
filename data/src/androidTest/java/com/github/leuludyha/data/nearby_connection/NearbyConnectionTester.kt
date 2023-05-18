package com.github.leuludyha.data.nearby_connection

import android.content.Context
import com.google.android.gms.nearby.connection.ConnectionsClient

class NearbyConnectionTester(
    context: Context, username: String, client: ConnectionsClient
) : NearbyConnectionImpl(
    context, username, client = client
) {

    val internalState get() = super.state
}