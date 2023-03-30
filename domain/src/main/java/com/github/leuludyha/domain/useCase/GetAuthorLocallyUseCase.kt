package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetAuthorLocallyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(authorId: String) =
        libraryRepository.getAuthorLocally(authorId)
}