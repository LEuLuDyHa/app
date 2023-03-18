package com.github.leuludyha.ibdb.presentation.screen.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.repository.OneTapSignInResponse
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.github.leuludyha.domain.model.Result
import com.github.leuludyha.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

@HiltViewModel
class SignInViewModel @Inject constructor(
    val oneTapClient: SignInClient, // TODO AR
    private val signInUseCases: SignInUseCases
) : ViewModel() {

    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Result.Loading())
        private set
    var firebaseSignInResponse by mutableStateOf<SignInWithGoogleResponse>(Result.Loading())
        private set

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = signInUseCases.oneTapSignInUseCase.invoke()
    }

    fun firebaseSignIn(googleCredential: AuthCredential) = viewModelScope.launch {
        firebaseSignInResponse = signInUseCases.firebaseSignInUseCase.invoke(googleCredential)
    }

}
