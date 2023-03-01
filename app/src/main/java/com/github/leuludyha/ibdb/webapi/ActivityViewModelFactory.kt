package com.github.leuludyha.ibdb.webapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.leuludyha.ibdb.database.ActivityRepository

class ActivityViewModelFactory(private val repository: ActivityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebApiActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WebApiActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}