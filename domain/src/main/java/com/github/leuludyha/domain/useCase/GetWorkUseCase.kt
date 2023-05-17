package com.github.leuludyha.domain.useCase

import android.content.Context
import com.github.leuludyha.domain.model.library.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class GetWorkUseCase(
    private val getWorkLocallyUseCase: GetWorkLocallyUseCase,
    private val getWorkRemotelyUseCase: GetWorkRemotelyUseCase,
) {
    suspend operator fun invoke(workId: String, context: Context) =
        getWorkLocallyUseCase(workId).map{ Result.Success(it) }.firstOrNull()
            ?: getWorkRemotelyUseCase(context, workId).firstOrNull()
            ?: Result.Error("Could not fetch any work with this id")
}