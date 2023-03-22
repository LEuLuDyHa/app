package com.github.leuludyha.domain.model

import kotlinx.coroutines.flow.Flow

data class Work(
    val id: String,
    val title: String?,
    val authors: Flow<List<Author>>,
    val covers: Flow<List<Cover>>,
    val subjects: List<String>?,
)