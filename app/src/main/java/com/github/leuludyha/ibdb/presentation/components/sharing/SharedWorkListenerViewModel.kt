package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedWorkListenerViewModel @Inject constructor(
    authContext: AuthenticationContext
) : ViewModel() {

    val connection = authContext.nearbyConnection
}