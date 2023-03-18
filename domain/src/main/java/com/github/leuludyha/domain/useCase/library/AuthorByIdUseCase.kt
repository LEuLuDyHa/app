package com.github.leuludyha.domain.useCase.library

import com.github.leuludyha.domain.repository.LibraryRepository

class AuthorByIdUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(authorId: String) = libraryRepository.authorById(authorId)
}