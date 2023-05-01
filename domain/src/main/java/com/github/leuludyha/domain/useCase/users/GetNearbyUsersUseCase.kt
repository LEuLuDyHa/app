package com.github.leuludyha.domain.useCase.users

import com.github.leuludyha.domain.repository.UserRepository

class GetNearbyUsersUseCase (
    private val userRepository: UserRepository
) {
    operator fun invoke(latitudeMax: Double, longitudeMax: Double, latitudeMin: Double, longitudeMin: Double) =
        userRepository.getNearbyUsers(latitudeMax, longitudeMax, latitudeMin, longitudeMin)
}