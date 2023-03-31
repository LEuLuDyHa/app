package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["workId", "subjectName"])
data class WorkSubjectCrossRef(
    val workId: String,
    val subjectName: String,
)
