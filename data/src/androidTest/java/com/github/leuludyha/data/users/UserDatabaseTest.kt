package com.github.leuludyha.data.users

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserDatabaseTest {

    val database = Firebase.database.useEmulator("10.0.2.2", 9000)
}