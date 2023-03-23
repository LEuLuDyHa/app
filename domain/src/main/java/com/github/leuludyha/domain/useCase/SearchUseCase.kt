package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class SearchUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(query: String) = libraryRepository.search(query)
}