package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.interfaces.Keyed

/**
 * A user of the application, could be either
 * the [MainUser] or a [Friend]
 */
interface User : Keyed {

    val username: String

    val profilePictureUrl: String?

    val phoneNumber: String?
}