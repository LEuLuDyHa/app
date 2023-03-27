package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class WorksByAuthorIdUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(authorId: String) = libraryRepository.worksByAuthorId(authorId)
}