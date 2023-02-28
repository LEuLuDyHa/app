package com.github.leuludyha.ibdb.connectivity


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
