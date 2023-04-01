package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.UserDataSource
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

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
        return userDataSource.getNeighbouringUsersOf(user, distance, n)
    }
}