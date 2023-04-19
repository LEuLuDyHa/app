package com.github.leuludyha.data.users

import com.github.leuludyha.domain.model.user.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.CompletableFuture
import com.google.maps.android.SphericalUtil

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
            }
            else future.completeExceptionally(IllegalArgumentException("User not found"))
        }.addOnFailureListener {
            future.completeExceptionally(it)
        }
        return future
    }

    /**
     * Retrieves a list of all users from the Firebase Realtime Database.
     * @return A CompletableFuture that will eventually hold a List of User objects.
     * */
    fun getAllUsers(): CompletableFuture<List<User>> {
        val future = CompletableFuture<List<User>>()
        db.get().addOnSuccessListener { data ->
            val userList = mutableListOf<User>()
            if (data.exists()) {
                for (userSnapshot in data.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                future.complete(userList)
            } else future.completeExceptionally(IllegalArgumentException("No users found"))
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
                future.complete(null) }
            .addOnFailureListener {
                future.completeExceptionally(it) }

        return future
    }

    /**
     * Updates a user's information in the Firebase Realtime Database.
     * @param username The username of the user to update.
     * @param newUser The new User object with updated information.
     * @return A CompletableFuture that will eventually hold the updated User object if the operation was successful.
     */
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

    /**
     * Retrieves a list of users within a certain radius from a given latitude and longitude coordinates.
     * @param latitude The latitude coordinate to use as the center of the search radius.
     * @param longitude The longitude coordinate to use as the center of the search radius.
     * @param radius The radius of the search in meters.
     * @return A CompletableFuture that will eventually hold a List of User objects within the search radius.
     */
    fun getNearbyUsers(latitude: Double, longitude: Double, radius: Int): CompletableFuture<List<User>>{
       return getAllUsers().thenApply { users ->
           users.filter { user ->
               val userLatitude = user.latitude
               val userLongitude = user.longitude
               val distance = SphericalUtil.computeDistanceBetween(
                   LatLng(latitude, longitude),
                   LatLng(userLatitude, userLongitude)
               )
               distance < radius
           }
       }
    }
}