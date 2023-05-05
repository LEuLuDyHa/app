package com.github.leuludyha.ibdb.presentation.navigation

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.ibdb.MainActivity
import com.google.common.truth.Truth.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Field
import java.lang.reflect.Method


@RunWith(AndroidJUnit4::class)
class NavGraphTest {
    //TODO: If we really need to increase coverage further and go around hilt,
    // we can do this (which creates all necessary hilt injections for the composables)
    // Unfortunately, it is has a very poor maintainability, as any change will make the
    // tests fail

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun openMainActivityDoesNotCrash() {

    }

//    @Test
//    fun openMainActivityOfflineDoesNotCrash() {
//        setMobileDataEnabled(composeTestRule.activity, false)
//    }

    //https://stackoverflow.com/questions/15756609/temporarily-disabling-internet-access-in-android
    private fun setMobileDataEnabled(context: Context, enabled: Boolean) {
        val conman = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val conmanClass = Class.forName(conman.javaClass.name)
        val iConnectivityManagerField: Field = conmanClass.getDeclaredField("Service")
        iConnectivityManagerField.isAccessible = true
        val iConnectivityManager = iConnectivityManagerField.get(conman)
        val iConnectivityManagerClass = Class.forName(iConnectivityManager.javaClass.name)
        val setMobileDataEnabledMethod: Method = iConnectivityManagerClass.getDeclaredMethod(
            "setMobileDataEnabled",
            java.lang.Boolean.TYPE
        )
        setMobileDataEnabledMethod.isAccessible = true
        setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled)
    }
}