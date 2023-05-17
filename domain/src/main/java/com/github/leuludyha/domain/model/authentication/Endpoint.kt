package com.github.leuludyha.domain.model.authentication

import com.github.leuludyha.domain.model.interfaces.Keyed

/**
 * Information on an endpoint discovered
 * via a [NearbyConnection] object
 */
class Endpoint(
    val name: String,
    val id: String
) : Keyed {
    override fun Id() = id
}