package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class EditionByISBNUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(isbn: Long) = libraryRepository.editionByISBN(isbn)
}