package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository

class SaveWorkPrefLocallyUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(workPref: WorkPreference) =
        libraryRepository.saveLocally(workPref)
}