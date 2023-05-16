package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.interfaces.Keyed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareWorkComponentViewModel @Inject constructor(
    _authContext: AuthenticationContext
) : ViewModel() {

    /** Update the list of endpoints available */
    fun updateEndpointList() {
        // TODO Ideally, we should check that all found endpoints are users currently using our app
        endpointChoices.clear()
        endpointChoices.addAll(connection.getDiscoveredEndpointIds().map { Endpoint(it) })
    }

    val endpointChoices = mutableStateListOf<Endpoint>()

    val connection = _authContext.nearbyConnection

    class Endpoint(val id: String) : Keyed {
        override fun Id(): String = id
    }
}