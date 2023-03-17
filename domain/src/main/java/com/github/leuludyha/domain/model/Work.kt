package com.github.leuludyha.domain.model


data class Work(
    val title: String?,
    val id: String?,
    val authorIds: List<String>?,
    val coverIds: List<Int>?,
    val subjects: List<String>?,
)