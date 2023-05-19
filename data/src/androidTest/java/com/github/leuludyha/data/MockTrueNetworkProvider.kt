package com.github.leuludyha.data

import android.content.Context
import com.github.leuludyha.domain.util.NetworkProvider

object MockTrueNetworkProvider: NetworkProvider {
    override fun checkNetworkAvailable(context: Context): Boolean = true
}