package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity representing a `Subject` (which is a `string`).
 */
@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey
    val subjectName: String,
): Raw<String> {
    override fun toModel(libraryDao: LibraryDao): String = subjectName

    companion object {
        fun from(subject: String) = SubjectEntity(
            subjectName = subject
        )
    }
}