package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.user.User

fun hasContactPermission(context: Context): Boolean {
    // on below line checking if permission is present or not.
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS) ==
            PackageManager.PERMISSION_GRANTED
}

fun requestContactPermission(context: Context, activity: Activity) {
    // on below line if permission is not granted requesting permissions.
    if (!hasContactPermission(context)) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            1
        )
    }
}

data class UserFromContact(
    val contact: Contact,
    val user: User,
) : Keyed {
    override fun Id() = user.Id()

}

data class Contact(
    val name: String,
    val phoneNumber: String?,
    val email: String?,
) {

    private val googleEmailSuffix: String = "@gmail.com"

    /**
     * @return True if the email is not null and it ends with [googleEmailSuffix]
     * (It can be used by one of the existing user in the database)
     */
    fun isEmailValid() = email?.endsWith(googleEmailSuffix) == true

    /**
     * @return True if the phone number is not null nor blank, false otherwise
     */
    fun isPhoneNumberValid() = !phoneNumber.isNullOrBlank()

    /**
     * @return True if either the phone number or the email is not null
     */
    fun isValid() = isEmailValid() or isPhoneNumberValid()
}