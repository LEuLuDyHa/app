package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.OpenLibraryRepository

class SearchUseCase(private val movieRepository: OpenLibraryRepository) {
    suspend operator fun invoke(query: String)=movieRepository.search(query)
}