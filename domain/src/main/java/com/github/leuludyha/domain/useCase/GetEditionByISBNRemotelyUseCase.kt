package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetEditionByISBNRemotelyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(isbn: String) =
        libraryRepository.getEditionByISBNRemotely(isbn)
}