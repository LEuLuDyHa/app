package com.github.leuludyha.data.users

import com.github.leuludyha.domain.model.library.Mocks
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserDatabaseTest {

    private val userDB = UserDatabase()

    @Test
    fun getUserDoesNotCrash() {
        val future = userDB.getUser("username")
        assertThat(future.isDone).isFalse()
    }

    @Test
    fun getUserFromPhoneNumberDoesNotCrash() {
        val future = userDB.getUserFromPhoneNumber("username")
        assertThat(future.isDone).isFalse()
    }

    @Test
    fun getAllUsersDoesNotCrash() {
        val future = userDB.getAllUsers()
        assertThat(future.isDone).isFalse()
    }

    @Test
    fun getNearbyUsersDoesNotCrash() {
        val future = userDB.getNearbyUsers(10.0, 10.0, 0.0, 0.0)
        assertThat(future.isDone).isFalse()
    }

    @Test
    fun getNeighboringUsersDoesNotCrash() {
        val neighbors = userDB.getNeighbouringUsersOf(Mocks.mainUser, { _, _ -> 0f }, 1)
        assertThat(neighbors).isEqualTo(listOf(Mocks.mainUser))
    }
}