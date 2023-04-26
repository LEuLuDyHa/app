package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [WorkEntity] and many [SubjectEntity]
 */
data class WorkWithSubjects(
    @Embedded val work: WorkEntity,
    @Relation(
        parentColumn = "workId",
        entityColumn = "subjectName",
        associateBy = Junction(WorkSubjectCrossRef::class)
    )
    val subjects: List<SubjectEntity>
)
