package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Loaded.LoadedWork
import com.github.leuludyha.domain.model.library.Work
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class DeserializeWorkUseCase {

    fun invoke(workJsonString: String): Work =
        Json.decodeFromString<LoadedWork>(workJsonString).toWork()

}