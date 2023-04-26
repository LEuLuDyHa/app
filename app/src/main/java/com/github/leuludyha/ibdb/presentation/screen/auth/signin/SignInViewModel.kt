package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the SignIn screen,
 * gives the UI access to the responses current values
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    val oneTapClient: SignInClient, // TODO AR
    private val signInUseCases: SignInUseCases,
) : ViewModel() {

    // variables keep track of the state of the request and can be observed by the UI
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse?>(null)
        private set

    //    var firebaseSignInResponse by mutableStateOf<SignInWithGoogleResponse>(Result.Loading())
    var firebaseSignInResponse by mutableStateOf<SignInWithGoogleResponse?>(null)
        private set

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Result.Loading()
        oneTapSignInResponse = signInUseCases.oneTapSignInUseCase.invoke()
    }

    fun firebaseSignIn(googleCredential: AuthCredential) = viewModelScope.launch {
        firebaseSignInResponse = Result.Loading()
        firebaseSignInResponse = signInUseCases.firebaseSignInUseCase.invoke(googleCredential)
    }

}
