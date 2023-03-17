package com.github.leuludyha.domain.useCase.auth.signin

data class SignInUseCases(
    val oneTapSignInUseCase: OneTapSignInUseCase,
    val firebaseSignInUseCase: FirebaseSignInUseCase
)
