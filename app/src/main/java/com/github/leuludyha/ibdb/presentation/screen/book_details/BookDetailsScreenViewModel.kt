package com.github.leuludyha.ibdb.presentation.screen.book_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.useCase.GetWorkRemotelyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsScreenViewModel @Inject constructor(
    _authContext: AuthenticationContext,
    private val bookById: GetWorkRemotelyUseCase,
) : ViewModel() {

    private val _work = MutableStateFlow<Result<Work>?>(null)
    val work = _work

    val authContext = _authContext

    fun loadWorkFrom(context: Context, workId: String) {
        viewModelScope.launch {
            if (_work.value == null) {
                bookById(context, workId).collect {
                    _work.value = it
                }
            }
        }
    }
}