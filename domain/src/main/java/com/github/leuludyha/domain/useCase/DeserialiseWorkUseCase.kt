package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Loaded.LoadedWork
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class DeserializeWorkUseCase(private val libraryRepository: LibraryRepository) {

    suspend fun invoke(workJson: String) {
        val work = Json.decodeFromString<LoadedWork>(workJson).toWork()
        libraryRepository.saveLocally(work)
    }

}