package com.github.leuludyha.data.repository

import com.github.leuludyha.data.users.UserDatabase
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.repository.UserRepository
import java.util.concurrent.CompletableFuture

class UserRepositoryImpl(
    private val userDatabase: UserDatabase
) : UserRepository {

    override fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User> {
        return userDatabase.getUserFromPhoneNumber(phoneNumber)
    }
}