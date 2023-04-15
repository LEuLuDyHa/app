package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.repository.LibraryRepository

class SaveEditionLocallyUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(edition: Edition) =
        libraryRepository.saveEditionLocally(edition)
}