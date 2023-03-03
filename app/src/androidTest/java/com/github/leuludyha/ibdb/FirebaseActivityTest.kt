package com.github.leuludyha.ibdb

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.junit.Rule
import org.junit.Test

class FirebaseActivityTest {

    // 10.0.2.2 is the special IP address to connect to the 'localhost' of
    // the host computer from an Android emulator.
    val database = Firebase.database

    @get:Rule
    val composeTestRule = createAndroidComposeRule<FirebaseActivity>()

    @Test
    fun setAndGet(){
        database.useEmulator("10.0.2.2", 9000)



        // With a DatabaseReference, write null to clear the database.
        database.reference.setValue(null)


    }
}