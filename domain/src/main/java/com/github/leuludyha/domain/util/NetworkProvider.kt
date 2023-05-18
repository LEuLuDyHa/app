package com.github.leuludyha.domain.util

import android.content.Context

interface NetworkProvider {
    fun checkNetworkAvailable(context: Context): Boolean
}