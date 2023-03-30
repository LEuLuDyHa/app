package com.github.leuludyha.ibdb.presentation.components.search

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
class BookSearchViewModel @Inject constructor(
    private var useCase: SearchRemotelyUseCase
) : ViewModel() {

    private val _searchedWorks = MutableStateFlow<PagingData<Work>>(PagingData.empty())
    val searchedWorks = _searchedWorks

    private val _queryLoading = mutableStateOf(false)
    val queryLoading = _queryLoading

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _isReadingISBN = mutableStateOf(false)
    val isReadingISBN = _isReadingISBN

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateIsReadingISBN(isReadingISBN: Boolean) {
        _isReadingISBN.value = isReadingISBN
    }

    fun searchWorks(query: String) {
        _queryLoading.value = true
        viewModelScope.launch {
            useCase(query).cachedIn(viewModelScope).collect {
                _searchedWorks.value = it
                _queryLoading.value = false
                _isReadingISBN.value = false
            }
        }
    }
}