package com.github.leuludyha.domain.model.user.preferences

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Work

class UserStatistics(
    /** List of preferred subjects of a user ordered by preference (idx 0 is highest) */
    val preferredSubjects: List<String>,
    /** List of preferred [Author] of a user ordered by preference (idx 0 is highest) */
    val preferredAuthors: List<Author>,
    /** List of preferred [Work] of a user ordered by preference (idx 0 is highest) */
    val preferredWorks: List<Work>,
    /** Average number of pages in the works this user marked as "read" */
    val averageNumberOfPages: Int,
)

