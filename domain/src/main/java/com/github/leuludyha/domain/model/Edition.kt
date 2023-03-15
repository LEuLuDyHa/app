package com.github.leuludyha.domain.model

data class Edition(
    val title: String?,
    val id: String?,
    val authorIds: List<String>?,
    val workIds: List<String>?,
    val coverIds: List<Int>?,
)
