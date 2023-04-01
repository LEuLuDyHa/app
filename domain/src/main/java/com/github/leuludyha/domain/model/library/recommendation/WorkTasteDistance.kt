package com.github.leuludyha.domain.model.library.recommendation

import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.library.recommendation.nn.LearnableWeight
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.ibdb.util.normalizedDifference

class WorkTasteDistance(
    private val learnableWeights: List<LearnableWeight> = listOf(
        LearnableWeight(6f),
        LearnableWeight(9f),
        LearnableWeight(6f),
    )
) {

    /**
     * Compute the taste distance between a work and a user.
     * A distance of 0 means the user's taste are a perfect match for the work
     */
    operator fun invoke(
        user: User, work: Work
    ): Float {
        val stats = user.statistics

        val values = listOf(
            1f - stats.averageNumberOfPages.normalizedDifference(work.nbOfPages),
            1f, // TODO 1 - stats.preferredSubjects.similarity(work.subjects),
            1f, // TODO 1 - stats.preferredAuthors.similarity(work.authors),
        )

        var distance = 0.0f
        values.forEachIndexed { index, value -> distance += value * learnableWeights[index].weight }

        return distance / values.size
    }

}