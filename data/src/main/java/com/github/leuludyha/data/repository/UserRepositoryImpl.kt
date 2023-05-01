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

    override fun getNearbyUsers(
        latitudeMax: Double,
        longitudeMax: Double,
        latitudeMin: Double,
        longitudeMin: Double
    ): CompletableFuture<List<User>> {
        return userDatabase.getNearbyUsers(latitudeMax, longitudeMax, latitudeMin, longitudeMin)
    }

    override fun getNeighbouringUsersOf(
        user: User,
        distance: (User, User) -> Float,
        n: Int
    ): List<User> {
        return userDatabase.getNeighbouringUsersOf(user, distance, n)
    }
}