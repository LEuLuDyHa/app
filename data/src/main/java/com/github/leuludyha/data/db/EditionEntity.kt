package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "editions")
data class EditionEntity (
    @PrimaryKey
    val editionId: String,
    val title: String?,
)