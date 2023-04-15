package com.github.leuludyha.domain.model.library.recommendation

import com.github.leuludyha.domain.model.library.recommendation.nn.Weight
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.util.normalizedDifference
import com.github.leuludyha.ibdb.util.similarity

class UserTasteDistance(
    private val weights: List<Weight> = listOf(
        Weight(1f),
        Weight(5f),
        Weight(3f),
        Weight(9f),
        Weight(0.25f),
    )
) {

    /**
     * Compute the distance in reading taste between two users of the app
     * The distance is based on different preferences and statistics
     * @param user1 First user
     * @param user2 Second user
     */
    operator fun invoke(user1: User, user2: User): Float {
        val values = listOf(
            // Friends similarity
            1 - user1.friends.similarity(user2.friends),
            // Authors similarity
            1 - user1.statistics.preferredAuthors.similarity(user2.statistics.preferredAuthors),
            // Subjects similarity
            1 - user1.statistics.preferredSubjects.similarity(user2.statistics.preferredSubjects),
            // Works similarity
            1 - user1.statistics.preferredWorks.similarity(user2.statistics.preferredWorks),
            // Average number of pages similarity
            user1.statistics.averageNumberOfPages.normalizedDifference(user2.statistics.averageNumberOfPages),
        )

        var distance = 0.0f
        values.forEachIndexed { index, value -> distance += value * weights[index].value }

        return distance / values.size
    }
}