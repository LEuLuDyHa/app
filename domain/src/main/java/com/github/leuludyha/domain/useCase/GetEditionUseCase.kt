package com.github.leuludyha.domain.useCase

import android.content.Context
import com.github.leuludyha.domain.model.library.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class GetEditionUseCase(
    private val getEditionLocallyUseCase: GetEditionLocallyUseCase,
    private val getEditionRemotelyUseCase: GetEditionRemotelyUseCase
) {
    suspend operator fun invoke(editionId: String, context: Context) =
        getEditionLocallyUseCase(editionId).map{ Result.Success(it) }.firstOrNull()
            ?: getEditionRemotelyUseCase(context, editionId).firstOrNull()
            ?: Result.Error("Could not fetch any work with this id")}