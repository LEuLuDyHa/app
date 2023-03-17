package com.github.leuludyha.domain.useCase.auth.signin

import com.github.leuludyha.domain.repository.AuthRepository

class OneTapSignInUseCase(private val authRepository: AuthRepository) {

    suspend fun invoke() = authRepository.oneTapSignInWithGoogle()

}
