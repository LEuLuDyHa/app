package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository

class GetWorkRemotelyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(workId: String) =
        libraryRepository.getWorkRemotely(workId)
}