package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class SearchRemotelyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(query: String) =
        libraryRepository.searchRemotely(query)
}