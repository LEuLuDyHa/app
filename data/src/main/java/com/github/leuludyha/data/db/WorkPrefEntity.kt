package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.user.preferences.WorkPreference

/**
 * A database entity representing a [WorkPreference].
 *
 * @param work the work attached to this preference
 * @param readingState the reading state of the attached work
 * @param possessed if the main user possesses the attached work
 * @param rating if present: from 0 to 1, if absent: the work was not rated
 */
@Entity(tableName = "workPrefs")
data class WorkPrefEntity (
    @PrimaryKey
    val work: WorkEntity,
    val readingState: WorkPreference.ReadingState,
    val possessed: Boolean,
    val rating: Float?
): Raw<WorkPreference> {
    override fun toModel(libraryDao: LibraryDao): WorkPreference {
        return WorkPreference(
            work = work.toModel(libraryDao),
            readingState = readingState,
            possessed = possessed,
            rating = rating
        )
    }

    companion object {
        fun from(workPref: WorkPreference) = WorkPrefEntity(
            work = WorkEntity.from(workPref.work),
            readingState = workPref.readingState,
            possessed = workPref.possessed,
            rating = workPref.rating
        )
    }
}