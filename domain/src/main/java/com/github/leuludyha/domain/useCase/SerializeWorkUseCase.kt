package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Work
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class SerializeWorkUseCase {

    fun invoke(work: Work): String =
        Json.encodeToString(work.toLoadedWork())

}