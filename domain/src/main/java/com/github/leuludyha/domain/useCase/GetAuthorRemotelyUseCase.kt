package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetAuthorRemotelyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(authorId: String) =
        libraryRepository.getAuthorRemotely(authorId)
}