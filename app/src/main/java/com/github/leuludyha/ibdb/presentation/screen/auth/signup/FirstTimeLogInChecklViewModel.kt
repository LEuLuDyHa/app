package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.ibdb.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstTimeLogInChecklViewModel @Inject constructor(
    _authContext: AuthenticationContext
) : ViewModel() {

    val authContext = _authContext

    private var walkThroughCompleted by mutableStateOf(false)

    /**
     * Saves a boolean which indicates that the walk-through has been completed once
     * by the user. The boolean is persisted through sessions (Application restarts)
     */
    fun rememberWalkThroughIsCompleted(context: Context) {
        val sp = context.getSharedPreferences(
            Constant.SIGN_UP_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        val spEditor = sp.edit()
        spEditor.putBoolean(Constant.SIGN_UP_WALKTHROUGH_COMPLETED, true)
        spEditor.apply()
        walkThroughCompleted = true
    }

    /**
     * @return True if the walk through was already completed, false otherwise. The result
     * is persisted through sessions (Application restarts)
     */
    fun isWalkThroughCompleted(context: Context): Boolean {
        val sp = context.getSharedPreferences(
            Constant.SIGN_UP_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        walkThroughCompleted = sp.getBoolean(Constant.SIGN_UP_WALKTHROUGH_COMPLETED, false)

        return walkThroughCompleted
    }

    /**
     * Saves to persistent storage the options that have been chosen during the first log into the app.
     * This includes:
     * - preferred username
     * - dark theme preference
     */
    fun persistWalkthroughOptions(context: Context) {
        //TODO: Once that is implemented, we should also add all other options that are done during the walkthrough
        val sp = context.getSharedPreferences(
            Constant.SIGN_UP_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        val spEditor = sp.edit()
        spEditor.putString(Constant.PREFERRED_USERNAME, authContext.principal.username)
        spEditor.putBoolean(
            Constant.DARK_THEME_PREFERENCE,
            authContext.principal.userPreferences.darkTheme.value
        )
        spEditor.apply()
    }

    /**
     * Loads the walkthrough preferences on the authentication context.
     */
    fun updateWalkthroughPreferences(context: Context) {
        val sp = context.getSharedPreferences(
            Constant.SIGN_UP_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        authContext.principal.username =
            sp.getString(Constant.PREFERRED_USERNAME, authContext.principal.username)!!
        authContext.principal.userPreferences.darkTheme.value =
            sp.getBoolean(Constant.DARK_THEME_PREFERENCE, false)
    }
}