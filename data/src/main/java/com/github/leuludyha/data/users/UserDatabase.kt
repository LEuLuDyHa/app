package com.github.leuludyha.data.users

import com.github.leuludyha.domain.model.user.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.CompletableFuture

/**
 * This class provides access to the Firebase Realtime Database for storing and retrieving user data.
 */
class UserDatabase {

    private val db: DatabaseReference = Firebase.database.getReference("users")

    /**
     * Retrieves a user object for the given username from the Firebase Realtime Database.
     * @param username The username of the user to retrieve.
     * @return A CompletableFuture that will eventually hold the User object for the given username.
     *         If the user is not found, the CompletableFuture will complete exceptionally with an IllegalArgumentException.
     */
    fun getUser(username: String): CompletableFuture<User> {
        val future = CompletableFuture<User>()
        db.child(username).get().addOnSuccessListener { data ->
            if (data.exists()) {
                val user = data.getValue(User::class.java)
                future.complete(user)
            } else future.completeExceptionally(IllegalArgumentException("User not found"))
        }.addOnFailureListener {
            future.completeExceptionally(it)
        }
        return future
    }

    /**
     * Retrieves a user object for the given phone number from the Firebase Realtime Database
     * @param phoneNumber The phone number of the user to retrieve
     * @retrun A CompletableFuture that will eventually hold the User object for the given phone number.
     *         If the user is not found, the CompletableFuture will complete exceptionally with an IllegalArgumentException.
     */
    fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User> {
        val future = CompletableFuture<User>()
        db.equalTo(phoneNumber, "phoneNumber").get().addOnSuccessListener { data ->
            if (data.exists()) {
                val user = data.getValue(User::class.java)
                future.complete(user)
            } else future.completeExceptionally(IllegalArgumentException("User not found"))
        }.addOnFailureListener {
            future.completeExceptionally(it)
        }
        return future
    }

    /**
     * Adds a new user to the Firebase Realtime Database.
     * @param user The User object to add.
     * @return A CompletableFuture that will eventually complete with a null value if the operation was successful.
     *         If there's any error, the CompletableFuture will complete exceptionally with the exception.
     */
    fun addUser(user: User): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()

        db.child(user.username).setValue(user)
            .addOnSuccessListener {
                future.complete(null)
            }
            .addOnFailureListener {
                future.completeExceptionally(it)
            }

        return future
    }

    fun updateUserInfo(username: String, newUser: User): CompletableFuture<User> {
        val future = CompletableFuture<User>()

        db.child(username).setValue(newUser)
            .addOnSuccessListener {
                future.complete(newUser)
            }
            .addOnFailureListener { exception ->
                future.completeExceptionally(exception)
            }

        return future
    }

    fun getNearbyUsers(
        longitude: Double,
        latitude: Double,
        radius: Int
    ): CompletableFuture<List<User>> {
        TODO("not yet implemented")
    }

}