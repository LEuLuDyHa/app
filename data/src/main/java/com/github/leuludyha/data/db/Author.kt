package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author (
    @PrimaryKey
    val authorId: String,
    val wikipedia: String?,
    val name: String?,
    val bio: String?,
    val entityType: String?,
)