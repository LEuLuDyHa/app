package com.github.leuludyha.ibdb.presentation.screen

import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    _authContext: AuthenticationContext
) : ViewModel() {
    val authContext = _authContext
}