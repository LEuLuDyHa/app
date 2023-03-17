package com.github.leuludyha.domain.model

data class Work(
    var pk: Long = 0,
    val title: String?,
    val id: String?,
    val authorIds: List<String>?,
    val covers: List<Cover>?,
    val subjects: List<String>?,
)