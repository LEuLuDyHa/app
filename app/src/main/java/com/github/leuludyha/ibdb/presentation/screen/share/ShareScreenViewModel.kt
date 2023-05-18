package com.github.leuludyha.ibdb.presentation.screen.share

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.authentication.Endpoint
import com.github.leuludyha.domain.useCase.SerializeWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareScreenViewModel @Inject constructor(
    private val serializeWorkUseCase: SerializeWorkUseCase
) : ViewModel() {

    private val _workJson = MutableStateFlow<String?>(null)
    val workJson = _workJson

    fun loadWorkJsonFrom(workId: String) {
        viewModelScope.launch {
            if (_workJson.value == null) {
                serializeWorkUseCase(workId).collect {
                    _workJson.value = it
                }
            }
        }
    }

}