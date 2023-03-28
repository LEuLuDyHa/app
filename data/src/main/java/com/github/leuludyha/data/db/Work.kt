package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "works")
data class Work(
    @PrimaryKey
    val workId: String,
    val title: String?,
)