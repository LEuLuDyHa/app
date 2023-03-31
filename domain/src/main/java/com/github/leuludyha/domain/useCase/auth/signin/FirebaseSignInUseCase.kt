package com.github.leuludyha.domain.useCase.auth.signin

import com.github.leuludyha.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthCredential

class FirebaseSignInUseCase(private val authRepository: AuthRepository) {

    suspend fun invoke(googleCredential: AuthCredential) = authRepository.firebaseSignInWithGoogle(googleCredential)

}
