package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.repository.LibraryRepository

class DeleteEditionLocallyUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(edition: Edition) =
        libraryRepository.deleteLocally(edition)
}