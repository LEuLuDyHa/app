package com.github.leuludyha.domain.useCase

import android.content.Context
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.util.NetworkProvider
import kotlinx.coroutines.flow.flowOf

class GetAuthorRemotelyUseCase(
    private val libraryRepository: LibraryRepository,
    private val networkProvider: NetworkProvider
) {
    operator fun invoke(context: Context, authorId: String) =
        if(networkProvider.checkNetworkAvailable(context))
            libraryRepository.getAuthorRemotely(authorId)
        else
            flowOf(Result.Error("No internet connection"))
}