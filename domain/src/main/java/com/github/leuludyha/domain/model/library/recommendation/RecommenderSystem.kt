package com.github.leuludyha.domain.model.library.recommendation

import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.library.recommendation.nn.LearnableWeight
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.UserRepository
import com.github.leuludyha.domain.util.Constants
import com.github.leuludyha.ibdb.util.normalizedWeights
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.streams.toList

private const val DebugTag = "RecommenderSystem"

class RecommenderSystem(
    private val userRepository: UserRepository,
    private val lr: Float = 5e-5f,
) {

//========== ======== ==== ==
//        USER
//========== ======== ==== ==

    private val userTasteDistance: UserTasteDistance = UserTasteDistance()

//========== ======== ==== ==
//        WORK
//========== ======== ==== ==

    private val workTasteDistance: WorkTasteDistance = WorkTasteDistance()

//========== ======== ==== ==
//        NEIGHBOURS
//========== ======== ==== ==

    private val neighbourWeight = LearnableWeight(1.0f)
    private val workTasteWeight = LearnableWeight(4.0f)

//========== ======== ==== ==
//        RECOMMENDATIONS MEMORY
//========== ======== ==== ==

    /**
     * The set of all recommendations' id made to a given user by this model
     * The model will not be able to output one of the works in this set for a user
     * as it already recommended it to them. The set can be cleared using the
     * dedicated function [clearRecommendationsMemoryFor]
     */
    private val recommendationsMadeFor: MutableMap<User, MutableSet<String>> = mutableMapOf()

    /**
     * Map inputs of the model to its recorded outputs
     */
    private val records: MutableMap<User, Map<Work, Float>> = mutableMapOf()

    /**
     * Clear the set of recommendations made for a user so that
     * works previously in this set can be recommended again
     */
    fun clearRecommendationsMemoryFor(user: User) {
        TODO("Not Yet Implemented")
        // recommendationsMadeFor[user]?.clear()
    }

//========== ======== ==== ==
//        FORWARD
//========== ======== ==== ==

    /**
     * Forward for the model
     * @param user The user to get the recommendation to
     * @param maxNumberOfConsideredSuggestions The maximum number of works first considered for a
     * suggestion in the pool of the user's neighbours works
     * @param removeInterested Remove the works the user is already interested in
     */
    operator fun invoke(
        user: User,
        maxNumberOfConsideredSuggestions: Int = 200,
        removeInterested: Boolean = false,
        actFunction: ActivationFunction = Sigmoid()
    ): List<Work> {
        // Find all previous recommendations for the user
        // val previousRecommendations = recommendationsMadeFor[user] ?: emptySet()
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
        // List<Neighbours> to User
        val neighbours = userRepository.getNeighbouringUsersOf(user, { u1, u2 ->
            userTasteDistance(u1, u2)
        }, Constants.RECOMMENDATIONS_NB_OF_NEIGHBOURS)

        println("$DebugTag :: Neighbours found : $neighbours")

        if (neighbours.contains(user)) {
            throw IllegalStateException(
                "The user ${user.username} should not be in its own neighbours list."
            )
        }

        // Map<Neighbour, Distance>
        // Weights in [0, +oo]
        val neighbourDistances = neighbours.associateWith { userTasteDistance(it, user) }
        println("$DebugTag :: Neighbour distances : $neighbourDistances")
        // Map<Neighbour, Weight>
        // Weights in [0, 1] where the sum of all weights is 1
        val neighbourWeightFor = neighbourDistances.normalizedWeights()
        println("$DebugTag :: Neighbour weights : $neighbourWeightFor")

        var nbOfWorks = 0
        // All works read linked to their reader among the neighbours
        val worksReadBy = neighbours.associateWith { neighbour ->
            val readWorks = neighbour.preferences.workPreferences.values
                .stream()
                .filter { it.readingState == WorkPreference.ReadingState.FINISHED }
                .map { it.work }
                .filter { work ->
                    val workPreferences = user.preferences.workPreferences

                    val isAlreadyInterested =
                        (workPreferences[work.id]?.readingState == WorkPreference.ReadingState.INTERESTED) and !removeInterested

                    val isNotInReadingList = !workPreferences.containsKey(work.id)

                    isNotInReadingList or (isAlreadyInterested and !removeInterested)
                    // Note : We do not filter out already recommended works
                    // because that would make backpropagation more complicated
                }.toList()
                // Sort by distance, from smallest to largest : Get preferred books first
                .sortedBy { workTasteDistance(user, it) }
            nbOfWorks += readWorks.size

            readWorks
        }
        println("$DebugTag :: Works read : $worksReadBy")

        // The number of works to select
        val initialAvailableWorks = max(worksReadBy.size, maxNumberOfConsideredSuggestions)
        var worksToTake = initialAvailableWorks
        val selectedWorks: MutableList<Work> = mutableListOf()
        val selectedWorkNeighbourWeight: MutableMap<Work, Float> = mutableMapOf()

        println("$DebugTag :: Initial Available Works : $initialAvailableWorks")

        // Select a pool of book determined by the weight of the neighbour
        for (neighbour in neighbours) {
            // We can safely assume each user has a weight
            val neighbourWeight = neighbourWeightFor[neighbour]!!

            println("$DebugTag :: ${neighbour.username}, w=$neighbourWeight :")

            var nbWorksTaken = round(initialAvailableWorks * neighbourWeight).toInt()
            println("$DebugTag :: \t -> 0 / Taken : $nbWorksTaken")
            // Only take if remains available books to select
            nbWorksTaken = min(nbWorksTaken, worksToTake)
            println("$DebugTag :: \t -> 1 / Taken : $nbWorksTaken")
            // Only take the available books in the neighbour's finished list
            nbWorksTaken = min(nbWorksTaken, worksReadBy[neighbour]!!.size)
            println("$DebugTag :: \t -> 1 / Taken : $nbWorksTaken")

            // Add the work to the list (We know index < size because of the above line)
            (0 until nbWorksTaken).map { index ->
                // Select the work in the list of finished works of the neighbour
                val selectedWork = worksReadBy[neighbour]!![index]!!
                selectedWorks.add(selectedWork)

                // Get weight if already selected
                val accWeight = selectedWorkNeighbourWeight[selectedWork] ?: 0.0f
                // Add neighbour weight
                // [1 is best, 0 is worse], all weights sum up to 1
                selectedWorkNeighbourWeight[selectedWork] = accWeight + neighbourWeight
            }
            // Update the remaining number of works to take
            worksToTake -= nbWorksTaken
        }
        // Invert order : Smallest weight is best, all in [0, +oo]
        // Normalize : Smallest weight is best, all in [0, 1] and sums to 1
        val normNeighbourDistFor = selectedWorkNeighbourWeight
            .mapValues { 1 - it.value }
            .normalizedWeights()

        println("$DebugTag :: Norm Neighbour Weight : $normNeighbourDistFor")

        /*
         TODO Now, across the selected works, find the closest in taste, based on
            - Number of times it appears in the list (It may come multiple times from different neighbours
            - The neighbour it comes from (A closest neighbours might have bigger weight)
            - Subject it has compared to preferred subjects
            - Author it has compared to preferred author
         */

        val normWorkDistFor: Map<Work, Float> = selectedWorks
            .associateWith { workTasteDistance(user, it) }
            .normalizedWeights()

        println("$DebugTag :: Norm Work Dist : $normWorkDistFor")

        // Combine metrics
        val finalDistFor = selectedWorks.associateWith {
            actFunction(
                normWorkDistFor[it]!! * workTasteWeight.weight
                        + normNeighbourDistFor[it]!! * workTasteWeight.weight
            )
        }

        println("$DebugTag :: Final Dist : $finalDistFor")

//        // We have our output, let's save it
//        records[user] = finalDistFor
//
//        // Take the smallest distance of all
//        val recommendation: Work = finalDistFor.minBy { it.value }.key;
//        // Add the recommendation in memory
//        if (!recommendationsMadeFor.contains(user)) { recommendationsMadeFor[user] = mutableSetOf() }
//        recommendationsMadeFor[user]!!.add(recommendation.id)

        // Selected works sorted by minimal dist
        return selectedWorks.toList().sortedBy { finalDistFor[it] }
    }

//========== ======== ==== ==
//        BACKWARD
//========== ======== ==== ==

    fun backward(
        user: User,
        recommendedWork: Work,
        goodRecommendation: Boolean
    ) {
        // The backpropagation must occur only on outputs of model that exist
        // assert(records.containsKey(user))

        // Get record
        // val outputWeights = records[user]

        // TODO Maybe
    }
}