package com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.useCase.DeleteWorkPrefLocallyUseCase
import com.github.leuludyha.domain.useCase.GetAllWorkPrefsLocallyUseCase
import com.github.leuludyha.domain.useCase.SaveWorkPrefLocallyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingStateControlViewModel @Inject constructor(
    val _getAllWorkPrefsLocallyUseCase: GetAllWorkPrefsLocallyUseCase,
    val _saveWorkPrefLocallyUseCase: SaveWorkPrefLocallyUseCase,
    val _deleteWorkPrefLocallyUseCase: DeleteWorkPrefLocallyUseCase,
) : ViewModel() {
    val workPreferences = _getAllWorkPrefsLocallyUseCase().map { list ->
        list.associateBy { it.work.id }
    }

    fun saveWorkPref(workPreference: WorkPreference) = viewModelScope.launch {
        _saveWorkPrefLocallyUseCase(workPreference)
    }

    fun deleteWorkPref(workPreference: WorkPreference) = viewModelScope.launch {
        _deleteWorkPrefLocallyUseCase(workPreference)
    }
}