package com.github.leuludyha.domain.useCase

import android.content.Context
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import com.github.leuludyha.domain.model.library.Result

class GetAuthorUseCase(
    private val getAuthorLocallyUseCase: GetAuthorLocallyUseCase,
    private val getAuthorRemotelyUseCase: GetAuthorRemotelyUseCase
) {
    suspend operator fun invoke(authorId: String, context: Context) =
        getAuthorLocallyUseCase(authorId).map{ Result.Success(it) }.firstOrNull()
            ?: getAuthorRemotelyUseCase(context, authorId).firstOrNull()
            ?: Result.Error("Could not fetch any work with this id")}