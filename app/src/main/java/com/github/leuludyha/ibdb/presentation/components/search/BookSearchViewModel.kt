package com.github.leuludyha.ibdb.presentation.components.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.useCase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private var useCase: SearchUseCase
) : ViewModel() {

    fun getAllBooks(query: String, callback: (List<Work>?) -> Unit) {
        viewModelScope.launch {
            val result = useCase.invoke(query)

            result.data?.let { callback(it) }
        }
    }

}