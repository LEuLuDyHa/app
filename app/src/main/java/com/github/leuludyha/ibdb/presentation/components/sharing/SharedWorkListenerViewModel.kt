package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Loaded.LoadedWork
import com.github.leuludyha.domain.useCase.SaveWorkLocallyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SharedWorkListenerViewModel @Inject constructor(
    authContext: AuthenticationContext,
    private val saveWorkLocallyUseCase: SaveWorkLocallyUseCase
) : ViewModel() {

    val connection = authContext.nearbyConnection

    fun saveWorkFromJson(workJson: String, nav: (String) -> Unit) {
        val work = Json.decodeFromString<LoadedWork>(workJson).toWork()

        viewModelScope.launch {
            saveWorkLocallyUseCase(work)
        }.invokeOnCompletion {
            nav(work.id)
        }
    }

}