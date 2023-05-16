package com.github.leuludyha.domain.useCase

import android.content.Context
import kotlinx.coroutines.flow.firstOrNull

class GetAuthorUseCase(
    private val getAuthorLocallyUseCase: GetAuthorLocallyUseCase,
    private val getAuthorRemotelyUseCase: GetAuthorRemotelyUseCase
) {
    suspend operator fun invoke(authorId: String, context: Context) =
        getAuthorLocallyUseCase(authorId).firstOrNull() ?: getAuthorRemotelyUseCase(context, authorId)
}