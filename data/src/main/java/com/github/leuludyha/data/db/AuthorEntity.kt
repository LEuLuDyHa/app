package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity (
    @PrimaryKey
    val authorId: String,
    val wikipedia: String?,
    val name: String?,
//    @ColumnInfo(name = "photo_ids")
//    val photoIds: List<Int>?,
    val bio: String?,
    val entityType: String?,
)