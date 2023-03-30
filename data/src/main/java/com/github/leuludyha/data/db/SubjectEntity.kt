package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey
    val subjectName: String,
): Raw<String> {
    override fun toModel(libraryDao: LibraryDao): String = subjectName
}