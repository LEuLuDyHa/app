package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.repository.datasource.UserDataSource
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.google.firebase.ktx.Firebase

class UserDataSourceImpl(
    private val firebase: Firebase
) : UserDataSource {

    override fun getNeighbouringUsersOf(
        user: User, distance: (User, User) -> Float, n: Int
    ): List<User> {
        /*
            TODO Find the least costly way to compute distance between a significant
            portion of users in the DB with firebase (Since it is non-relational)
        */
//        firebase.firestore.collection("user")
//            .orderBy()
//            .limit(n.toLong())
//            .get()
        return listOf(Mocks.user)
    }


}