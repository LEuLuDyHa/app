package com.github.leuludyha.data.users

import com.github.leuludyha.domain.model.user.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.CompletableFuture

class UserDatabase {

    private val db: DatabaseReference = Firebase.database.getReference("users")

    fun getUser(username: String): CompletableFuture<User> {
        TODO("not yet implemented")
    }

    fun addUser(user: User): CompletableFuture<Boolean> {
        TODO("not yet implemented")
    }

    fun updateUserInfo(username: String, newUser: User): CompletableFuture<User> {
        TODO("not yet implemented")
    }

    fun getNearbyUsers(longitude: Double, latitude: Double, radius: Int): CompletableFuture<List<User>>{
        TODO("not yet implemented")
    }

}