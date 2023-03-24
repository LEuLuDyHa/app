package com.github.leuludyha.ibdb.presentation.components.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.domain.useCase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private var useCase: SearchUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedWorks = MutableStateFlow<PagingData<Work>>(PagingData.empty())
    val searchedWorks = _searchedWorks

    private val _queryLoading = mutableStateOf(false)
    val queryLoading = _queryLoading
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateQueryLoading(loading: Boolean) {
        _queryLoading.value = loading
    }

    fun searchWorks(query: String) {
        viewModelScope.launch {
            useCase(query = query).cachedIn(viewModelScope).collect {
                _searchedWorks.value = it
                updateQueryLoading(false)
            }
        }
    }

}