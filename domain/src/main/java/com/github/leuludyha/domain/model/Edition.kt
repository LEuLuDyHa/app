package com.github.leuludyha.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "editions")
data class Edition(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "id")
    val id: String?,
    @ColumnInfo(name = "author_ids")
    val authorIds: List<String>?,
    @ColumnInfo(name = "work_ids")
    val workIds: List<String>?,
    @ColumnInfo(name = "covers")
    val coverUrls:  List<Cover>,
)
