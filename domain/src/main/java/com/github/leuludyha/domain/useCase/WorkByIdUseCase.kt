package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class WorkByIdUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(workId: String) = libraryRepository.workById(workId)
}