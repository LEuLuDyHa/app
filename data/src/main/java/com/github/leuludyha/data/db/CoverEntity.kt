package com.github.leuludyha.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "covers")
data class CoverEntity(
    @PrimaryKey @ColumnInfo("id")
    val id: Long
)
