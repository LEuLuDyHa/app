package com.github.leuludyha.domain.useCase.library

import com.github.leuludyha.domain.repository.LibraryRepository

class EditionByISBNUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(isbn: Long) = libraryRepository.editionByISBN(isbn)
}