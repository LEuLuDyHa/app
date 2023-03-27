package com.github.leuludyha.domain.model.authentication

import com.github.leuludyha.domain.model.user.User

/**
 * The authentication context contains
 * all information relative to authentication of a user in the app,
 * including the principal : The identity of the user currently logged in
 */
open class AuthenticationContext(
    val principal: User?,
) {

    object Empty : AuthenticationContext(null)
}