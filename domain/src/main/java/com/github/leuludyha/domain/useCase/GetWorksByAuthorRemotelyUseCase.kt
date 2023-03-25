package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetWorksByAuthorRemotelyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(authorId: String) =
        libraryRepository.getWorksByAuthorRemotely(authorId)
}