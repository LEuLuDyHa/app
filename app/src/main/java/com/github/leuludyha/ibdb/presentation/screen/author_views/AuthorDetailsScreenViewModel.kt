package com.github.leuludyha.ibdb.presentation.screen.author_views

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.useCase.GetAuthorRemotelyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorDetailsScreenViewModel @Inject constructor(
    _authContext: AuthenticationContext,
    private val authorById: GetAuthorRemotelyUseCase,
) : ViewModel() {

    private val _author = MutableStateFlow<Result<Author>?>(null)
    val author = _author

    val authContext = _authContext

    fun loadAuthorFrom(context: Context, authId: String) {
        viewModelScope.launch {
            if (_author.value == null) {
                authorById(context, authId).collect {
                    _author.value = it
                }
            }
        }
    }
}