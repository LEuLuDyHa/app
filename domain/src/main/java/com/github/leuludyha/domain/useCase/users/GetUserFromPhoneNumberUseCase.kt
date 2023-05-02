package com.github.leuludyha.domain.useCase.users

import com.github.leuludyha.domain.repository.UserRepository

class GetUserFromPhoneNumberUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(phoneNumber: String) = userRepository.getUserFromPhoneNumber(phoneNumber)
}