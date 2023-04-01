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
//        firebase.firestore.collection("user")
//            .orderBy("TODO")
//            .limit(n.toLong())
//            .get()
        return listOf(Mocks.user)
    }


}