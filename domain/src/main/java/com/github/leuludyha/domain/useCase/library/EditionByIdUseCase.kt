package com.github.leuludyha.domain.useCase.library

import com.github.leuludyha.domain.repository.LibraryRepository

class EditionByIdUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(editionId: String) = libraryRepository.editionById(editionId)
}