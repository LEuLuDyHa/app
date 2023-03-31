package com.github.leuludyha.domain.model.user.preferences

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.util.normalizedDifference
import com.github.leuludyha.ibdb.util.similarity

class UserStatistics(
    /** List of preferred subjects of a user ordered by preference (idx 0 is highest) */
    val preferredSubjects: List<String>,
    /** List of preferred [Author] of a user ordered by preference (idx 0 is highest) */
    val preferredAuthors: List<Author>,
    /** List of preferred [Work] of a user ordered by preference (idx 0 is highest) */
    val preferredWorks: List<Work>,
    /** List of [User] which are "closest" to this user (idx 0 is highest) */
    val closestUserMatches: List<User>,
    /** Average number of pages in the works this user marked as "read" */
    val averageNumberOfPages: Int,
)

/**
 *
 * Compute the distance in reading taste between two users of the app
 * The distance is based on different preferences and statistics
 * @param user1 First user
 * @param user2 Second user
 */
fun tasteDistanceBetween(user1: User, user2: User): Float {
    val values = listOf(
        // Friends similarity
        user1.friends.similarity(user2.friends),
        // Authors similarity
        user1.statistics.preferredAuthors.similarity(user2.statistics.preferredAuthors),
        // Subjects similarity
        user1.statistics.preferredSubjects.similarity(user2.statistics.preferredSubjects),
        // Works similarity
        user1.statistics.preferredWorks.similarity(user2.statistics.preferredWorks),
        // Average number of pages similarity
        user1.statistics.averageNumberOfPages.normalizedDifference(user2.statistics.averageNumberOfPages),
    )

    val weights = listOf(
        1, 5, 3, 9, 0.25
    )

    var distance = 0.0f
    values.forEachIndexed { index, value -> distance += value * weights[index].toFloat() }

    return distance / values.size
}

