package com.github.leuludyha.domain.repository

import com.github.leuludyha.domain.model.user.User

@FunctionalInterface
interface UserRepository {
    /**
     * @param user User to get the neighbours of
     * @param distance Distance to use to get the neighbours
     * @param n Number of nearest neighbours to return
     * @return a sorted list of [User], ranked from smallest distance to largest distance
     */
    fun getNeighbouringUsersOf(user: User, distance: (User, User) -> Float, n: Int): List<User>
}