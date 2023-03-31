package com.github.leuludyha.ibdb.presentation.screen.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.useCase.SearchRemotelyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchScreenViewModel @Inject constructor(
    private var useCase: SearchRemotelyUseCase
) : ViewModel() {

    private val _searchedWorks = MutableStateFlow<PagingData<Work>>(PagingData.empty())
    val searchedWorks = _searchedWorks

    private val _queryLoading = mutableStateOf(false)
    val queryLoading = _queryLoading

    fun searchWorks(query: String) {
        _queryLoading.value = true
        viewModelScope.launch {
            useCase(query).cachedIn(viewModelScope).collect {
                _searchedWorks.value = it
                _queryLoading.value = false
            }
        }
    }

}