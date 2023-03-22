package com.github.leuludyha.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "covers")
data class Cover(
    @PrimaryKey
    val coverId: Long,
    val coverUrl: String?
)
