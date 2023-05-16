package com.github.leuludyha.domain.useCase

import android.content.Context
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.util.NetworkProvider
import kotlinx.coroutines.flow.flowOf

class SearchRemotelyUseCase(
    private val libraryRepository: LibraryRepository,
    private val networkProvider: NetworkProvider
) {
    operator fun invoke(context: Context, query: String) =
        if(networkProvider.checkNetworkAvailable(context))
            libraryRepository.searchRemotely(query)
        else
            flowOf()
}