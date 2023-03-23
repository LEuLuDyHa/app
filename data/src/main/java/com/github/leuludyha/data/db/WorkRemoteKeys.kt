package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_remote_keys")
data class WorkRemoteKeys(
    @PrimaryKey val workId: String,
    val prevPage: Int?,
    val nextPage: Int?
)