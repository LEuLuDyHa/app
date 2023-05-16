package com.github.leuludyha.domain.useCase

import android.content.Context
import kotlinx.coroutines.flow.firstOrNull

class GetWorkUseCase(
    private val getWorkLocallyUseCase: GetWorkLocallyUseCase,
    private val getWorkRemotelyUseCase: GetWorkRemotelyUseCase,
) {
    suspend operator fun invoke(workId: String, context: Context) =
        getWorkLocallyUseCase(workId).firstOrNull() ?: getWorkRemotelyUseCase(context, workId)
}