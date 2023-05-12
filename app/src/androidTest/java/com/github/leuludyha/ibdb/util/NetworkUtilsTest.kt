package com.github.leuludyha.ibdb.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkUtilsTest {
    @Test
    fun checkNetworkAvailableDoesNotCrash() {
        NetworkUtils.checkNetworkAvailable(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun registerCallbackOnNetworkAvailableDoesNotCrash() {
        NetworkUtils.registerCallbackOnNetworkAvailable(
            InstrumentationRegistry.getInstrumentation().context
        ) { assert(true) }
    }
}