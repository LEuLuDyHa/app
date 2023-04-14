package com.github.leuludyha.ibdb.presentation.components.auth.signup.add_friends

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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