package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetEditionLocallyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(editionId: String) =
        libraryRepository.getEditionLocally(editionId)
}