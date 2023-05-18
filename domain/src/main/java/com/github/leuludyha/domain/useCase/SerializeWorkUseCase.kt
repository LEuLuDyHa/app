package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

/**
 * First load the work locally,
 * then loads all of its contained flows,
 * then serializes it to json.
 */
class SerializeWorkUseCase(private val libraryRepository: LibraryRepository) {

    operator fun invoke(workId: String): Flow<String> =
        libraryRepository.getWorkLocally(workId)
            .map { it.toLoadedWork() }
            .map { Json.encodeToString(it) }

}
