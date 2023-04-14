package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    _authContext: AuthenticationContext
) : ViewModel() {

    val authContext = _authContext

    /**
     * Saves a boolean which indicates that the walk-through has been completed once
     * by the user. The boolean is persisted through sessions (Application restarts)
     */
    fun rememberWalkThroughIsCompleted() {
        TODO("Not yet implemented")
    }

    /**
     * @return True if the walk through was already completed, false otherwise. The result
     * is persisted through sessions (Application restarts)
     */
    fun isWalkThroughCompleted(): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Save the state of the user
     */
    fun persistUserState() {
        TODO("Not yet implemented")
    }
}