package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository

class DeleteWorkPrefLocallyUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(workPreference: WorkPreference) =
        libraryRepository.deleteLocally(workPreference)
}