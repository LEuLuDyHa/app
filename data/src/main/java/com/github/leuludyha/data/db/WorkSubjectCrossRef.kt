package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Work

/**
 * Cross reference between a [WorkEntity] and a [SubjectEntity]
 */
@Entity(primaryKeys = ["workId", "subjectName"])
data class WorkSubjectCrossRef(
    val workId: String,
    val subjectName: String,
)  {
    companion object {
        fun from(work: Work, subjectName: String) = WorkSubjectCrossRef(
            workId = work.id,
            subjectName = subjectName
        )
    }
}
