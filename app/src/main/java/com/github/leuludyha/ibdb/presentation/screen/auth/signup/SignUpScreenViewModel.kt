package com.github.leuludyha.ibdb.presentation.screen.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    _authContext: AuthenticationContext
) : ViewModel() {

    val authContext = _authContext

    private var walkThroughCompleted by mutableStateOf(false)

    /**
     * Saves a boolean which indicates that the walk-through has been completed once
     * by the user. The boolean is persisted through sessions (Application restarts)
     */
    fun rememberWalkThroughIsCompleted() {
        walkThroughCompleted = true
        return
        TODO("Not yet implemented")
    }

    /**
     * @return True if the walk through was already completed, false otherwise. The result
     * is persisted through sessions (Application restarts)
     */
    fun isWalkThroughCompleted(): Boolean {
        return walkThroughCompleted
        TODO("Not yet implemented")
    }

    /**
     * Save the state of the user
     */
    fun persistUserState() {
        return
        TODO("Not yet implemented")
    }
}