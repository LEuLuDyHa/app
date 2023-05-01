package com.github.leuludyha.domain.repository

import com.github.leuludyha.domain.model.user.User
import java.util.concurrent.CompletableFuture

@FunctionalInterface
interface UserRepository {

    /**
     * Retrieves a user from a phone number
     */
    fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User>

    /**
     * Fetches and returns all users whose stored location in Firebase is within a defined latitude and longitude.
     *
     * @param latitudeMax Limits of the space
     * @param longitudeMax Limits of the space
     * @param latitudeMin Limits of the space
     * @param longitudeMin Limits of the space
     * @return a list of [User]
     */
    fun getNearbyUsers(latitudeMax: Double, longitudeMax: Double, latitudeMin: Double, longitudeMin: Double): CompletableFuture<List<User>>

    /**
     * @param user User to get the neighbours of
     * @param distance Distance to use to get the neighbours
     * @param n Number of nearest neighbours to return
     * @return a sorted list of [User], ranked from smallest distance to largest distance
     */
    fun getNeighbouringUsersOf(user: User, distance: (User, User) -> Float, n: Int): List<User>
}