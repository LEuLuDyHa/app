package com.github.leuludyha.ibdb.presentation.screen.openLibrary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import javax.inject.Inject

import dagger.hilt.android.lifecycle.HiltViewModel

import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.useCase.SearchUseCase
import com.github.leuludyha.domain.util.Result


@HiltViewModel
class OpenLibraryViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _searchState = mutableStateOf<Result<Search>>(Result.Loading())
    val searchState: State<Result<Search>> = _searchState

    init {
        // TODO work at initialization (display recently seen books, ...)
    }

    fun search(query: String) {
        viewModelScope.launch {
            _searchState.value =  searchUseCase(query)
        }
    }
}