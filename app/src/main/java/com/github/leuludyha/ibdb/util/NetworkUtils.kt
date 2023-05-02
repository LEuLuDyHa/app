package com.github.leuludyha.ibdb.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

/**
 * Auxiliary functions related to network connection.
 */
object NetworkUtils {
    /**
     * This is implemented following the recommendations from [here](https://stackoverflow.com/questions/56709604/check-for-internet-connectivity-on-api29).
     *
     * @return true if a network is available. Both wifi and mobile are acceptable.
     */
    fun checkNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkAvailability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return networkAvailability != null
                && networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Registers [callback] to be executed once any network connection is available.
     * Doesn't take any measures to make sure the callback is properly executed.
     *
     * This is implemented following [this](https://stackoverflow.com/questions/25678216/android-internet-connectivity-change-listener)
     */
    fun registerCallbackOnNetworkAvailable(context: Context, callback: () -> Unit) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                //take action when network connection is gained
                super.onAvailable(network)

                callback()
            }
        })
    }
}