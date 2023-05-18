package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.library.Loaded.LoadedWork
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class LoadedWorkTest {

    @Test
    fun serialization() {
        listOf(
            Mocks.work1984,
            Mocks.workMrFox,
            Mocks.workLaFermeDesAnimaux
        ).forEach { work ->
            val json = Json.encodeToString(work.toLoadedWork())
            val workFromJson = Json.decodeFromString<LoadedWork>(json).toWork()
            assertThat(workFromJson).isEqualTo(work)
        }
    }

}