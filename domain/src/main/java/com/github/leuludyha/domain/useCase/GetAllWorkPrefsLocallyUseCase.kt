package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetAllWorkPrefsLocallyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke() =
        libraryRepository.getAllWorkPrefsLocally()
}