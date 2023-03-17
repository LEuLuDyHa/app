package com.github.leuludyha.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @ColumnInfo(name = "wikipedia")
    val wikipedia: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "id")
    val id: String?,
    @ColumnInfo(name = "photo_ids")
    val photoIds: List<Int>?,
    @ColumnInfo(name = "bio")
    val bio: String?,
    @ColumnInfo(name = "entity_type")
    val entityType: String?,
)
