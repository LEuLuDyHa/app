package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.repository.LibraryRepository

class SaveAuthorLocallyUseCase(private val libraryRepository: LibraryRepository) {
    suspend operator fun invoke(author: Author) =
        libraryRepository.saveAuthorLocally(author)
}