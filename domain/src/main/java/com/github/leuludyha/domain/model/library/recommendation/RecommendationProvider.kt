package com.github.leuludyha.domain.model.library.recommendation

import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.model.user.preferences.tasteDistanceBetween
import com.github.leuludyha.domain.repository.UserRepository
import com.github.leuludyha.domain.util.Constants
import com.github.leuludyha.ibdb.util.mapSecond
import com.github.leuludyha.ibdb.util.toMap
import kotlinx.coroutines.flow.Flow
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.streams.toList

fun getBestRecommendationFor(
    user: User,
    userRepository: UserRepository,
    maxNumberOfConsideredSuggestions: Int = 40,
    removeInterested: Boolean = false
): Flow<Work> {
    /* Idea :
    - A possible way to to have efficient lookup is to only query on books already in the database
    - K-Nearest neighbours
        - Neighbours are the users who have smallest distance to you
        - Distance is defined as similarity in books liked, possessed, rated, commented on...
    - Weight w/ books in the "Recent" category (< 6 month release)
    - Weight w/ books in the "Best Seller/Classics" category
        - we can maybe simplify to # Biggest number of editions
    - Weight w/ books in the "Preferred subject(s)" of users
     */
    val neighbours = userRepository.getNeighbouringUsersOf(user, { u1, u2 ->
        tasteDistanceBetween(u1, u2)
    }, Constants.RECOMMENDATIONS_NB_OF_NEIGHBOURS)

    val neighbourDistances = neighbours.stream().map { Pair(it, tasteDistanceBetween(it, user)) }
    val maxDistance = neighbourDistances.map { it.second }.toList().max()
    // The normalized distance of the neighbours is their weights relative to the user
    val neighboursWeights = neighbourDistances.map { weight ->
        weight.mapSecond { it / maxDistance }
    }
    val neighboursWeightSum = neighboursWeights.map { it.second }.toList().sum()
    // Divide by sum of weights so that the sum of all weights sum up to one
    val neighbourToWeight = neighboursWeights.map { weight ->
        weight.mapSecond { it / neighboursWeightSum }
    }.toMap()

    var nbOfWorks = 0
    // All works read linked to their reader among the neighbours
    val worksReadBy = neighbours.stream().map { neighbour ->
        val readWorks = neighbour.preferences.workPreferences.values
            .stream()
            .filter { it.readingState == WorkPreference.ReadingState.FINISHED }
            .map { it.work }
            .filter { work ->
                val workPreferences = user.preferences.workPreferences

                // TODO FILTER WORK THAT WERE ALREADY RECOMMENDED
                val isAlreadyInterested =
                    (workPreferences[work.id]?.readingState == WorkPreference.ReadingState.INTERESTED) and !removeInterested
                val isNotInReadingList = !workPreferences.containsKey(work.id)

                isNotInReadingList or (isAlreadyInterested and !removeInterested)
            }.toList()
        nbOfWorks += readWorks.size
        Pair(neighbour, readWorks)
    }.toMap()

    // The number of works to select
    val initialAvailableWorks = max(worksReadBy.size, maxNumberOfConsideredSuggestions)
    var worksToTake = initialAvailableWorks
    val selectedWorks: MutableList<Work> = mutableListOf()

    // Select a pool of book determined by the weight of the neighbour
    for (neighbour in neighbours) {
        // We can safely assume each user has a weight
        val weight = neighbourToWeight[neighbour]!!

        var nbWorksTaken = round(initialAvailableWorks * weight).toInt()
        // Only take if remains available books to select
        nbWorksTaken = min(nbWorksTaken, worksToTake)
        // Only take the available books in the neighbour's finished list
        nbWorksTaken = min(nbWorksTaken, worksReadBy[neighbour]!!.size)

        // Add the work to the list (We know index < size because of the above line)
        (0 until nbWorksTaken).map { index -> selectedWorks.add(worksReadBy[neighbour]!![index]!!) }
        // Update the remaining number of works to take
        worksToTake -= nbWorksTaken
    }

    /*
     TODO Now, across the selected works, find the closest in taste, based on
        - Number of times it appears in the list (It may come multiple times from different neighbours
        - The neighbour it comes from (A closest neighbours might have bigger weight)
        - Subject it has compared to preferred subjects
        - Author it has compared to preferred author
     */

}