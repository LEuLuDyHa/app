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

    /**
     * @param user User to get the neighbours of
     * @param distance Distance to use to get the neighbours
     * @param n Number of nearest neighbours to return
     * @return a sorted list of [User], ranked from smallest distance to largest distance
     */
    override fun getNeighbouringUsersOf(
        user: User,
        distance: (User, User) -> Float,
        n: Int
    ): List<User> {
        return userDatabase.getNeighbouringUsersOf(user, distance, n)
    }
}