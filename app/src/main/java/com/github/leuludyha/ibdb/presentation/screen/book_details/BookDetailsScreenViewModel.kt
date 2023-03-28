package com.github.leuludyha.ibdb.presentation.screen.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Work
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsScreenViewModel @Inject constructor(
    _authContext: AuthenticationContext,
    // private val workByIdUseCase: WorkByIdUseCase,
) : ViewModel() {

    val authContext = _authContext

    fun getWorkFrom(workId: String, callback: (Work?) -> Unit) {
        viewModelScope.launch {
            // val result = workByIdUseCase.invoke(workId)

            // result.data?.let { callback(it) }
        }
    }
}