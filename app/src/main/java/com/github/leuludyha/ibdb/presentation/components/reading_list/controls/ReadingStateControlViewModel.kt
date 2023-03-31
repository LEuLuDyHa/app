package com.github.leuludyha.ibdb.presentation.components.reading_list.controls

import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadingStateControlViewModel @Inject constructor(
    _authContext: AuthenticationContext
) : ViewModel() {
    val userPreferences = _authContext.principal.preferences
}