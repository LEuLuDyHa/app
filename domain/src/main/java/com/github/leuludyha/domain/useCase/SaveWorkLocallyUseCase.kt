package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.repository.LibraryRepository

class SaveWorkLocallyUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(work: Work) =
        libraryRepository.saveWorkLocally(work)
}