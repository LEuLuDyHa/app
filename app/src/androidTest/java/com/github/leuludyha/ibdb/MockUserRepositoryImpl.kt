package com.github.leuludyha.ibdb

import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.repository.UserRepository
import java.util.concurrent.CompletableFuture

class MockUserRepositoryImpl: UserRepository {
    override fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User> {
        return CompletableFuture.completedFuture(Mocks.mainUser)
    }

    override fun getNearbyUsers(
        latitudeMax: Double,
        longitudeMax: Double,
        latitudeMin: Double,
        longitudeMin: Double
    ): CompletableFuture<List<User>> {
        return CompletableFuture.completedFuture(Mocks.userList)
    }

    override fun getNeighbouringUsersOf(
        user: User,
        distance: (User, User) -> Float,
        n: Int
    ): List<User> {
        return Mocks.userList
    }
}
