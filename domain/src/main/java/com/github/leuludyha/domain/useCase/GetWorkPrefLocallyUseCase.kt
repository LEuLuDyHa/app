package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetWorkPrefLocallyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(workId: String) =
        libraryRepository.getWorkPrefLocally(workId)
}