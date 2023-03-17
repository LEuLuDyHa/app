package com.github.leuludyha.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Author(
    var pk: Long = 0,
    val wikipedia: String?,
    val name: String?,
    val id: String?,
    val photoIds: List<Int>?,
    val bio: String?,
    val entityType: String?,
)
