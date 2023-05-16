package com.github.leuludyha.domain.useCase

import android.content.Context
import kotlinx.coroutines.flow.firstOrNull

class GetEditionUseCase(
    private val getEditionLocallyUseCase: GetEditionLocallyUseCase,
    private val getEditionRemotelyUseCase: GetEditionRemotelyUseCase
) {
    suspend operator fun invoke(authorId: String, context: Context) =
        getEditionLocallyUseCase(authorId).firstOrNull() ?: getEditionRemotelyUseCase(context, authorId)
}