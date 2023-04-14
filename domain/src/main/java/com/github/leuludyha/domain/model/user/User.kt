package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.interfaces.Keyed

/**
 * A user of the application, could be either
 * the [MainUser] or a [Friend]
 */
interface User : Keyed {

    fun username(): String

    fun profilePictureUrl(): String?
}