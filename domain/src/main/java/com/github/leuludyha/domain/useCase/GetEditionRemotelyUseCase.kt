package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetEditionRemotelyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(editionId: String) =
        libraryRepository.getEditionRemotely(editionId)
}