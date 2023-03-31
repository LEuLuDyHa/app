package com.github.leuludyha.domain.repository

import com.github.leuludyha.domain.model.user.User
import java.util.concurrent.CompletableFuture

interface UserRepository {

    /**
     * Retrieves a user from a phone number
     */
    fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User>
}