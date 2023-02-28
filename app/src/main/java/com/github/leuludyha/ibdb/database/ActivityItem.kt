package com.github.leuludyha.ibdb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entities are tables in the database. Each column is an attribute and each row is an item.
 */
@Entity(tableName = "activity_table")
data class ActivityItem(
    @PrimaryKey @ColumnInfo val activity: String,
)