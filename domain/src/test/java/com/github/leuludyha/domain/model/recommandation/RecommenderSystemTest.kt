package com.github.leuludyha.domain.model.recommandation


import com.github.leuludyha.domain.TestMocks
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.library.recommendation.RecommenderSystem
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.util.concurrent.CompletableFuture

class RecommenderSystemTest {

    lateinit var recommender: RecommenderSystem

    private fun initRecommender(
        f: (user: User, distance: (User, User) -> Float, n: Int) -> List<User>,
    ) {
        recommender = RecommenderSystem(
            object : UserRepository {
                override fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User> {
                    TODO("Not yet implemented")
                }

                override fun getNearbyUsers(
                    latitudeMax: Double,
                    longitudeMax: Double,
                    latitudeMin: Double,
                    longitudeMin: Double
                ): CompletableFuture<List<User>> {
                    TODO("Not yet implemented")
                }

                override fun getNeighbouringUsersOf(
                    user: User,
                    distance: (User, User) -> Float,
                    n: Int
                ): List<User> {
                    return f(user, distance, n)
                }
            }
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun recommenderReturnEmptyListOnNoUserInSystem() {
        runTest {
            initRecommender { _, _, _ -> listOf() }
            assertThat(
                "Should not crash on empty system",
                recommender(TestMocks.user1).first().isEmpty()
            )
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun recommenderReturnsUserWorksOnOneUserInSystem() {
        runTest {
            initRecommender { _, _, _ -> listOf(TestMocks.user1) }
            val recommendations = recommender(TestMocks.user2)
            assertThat("Should not be empty", recommendations.first().isNotEmpty())
            assertEquals(
                TestMocks.user1.preferences.getWorksInReadingList().toSet(),
                recommendations.first().toSet()
            )
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun recommenderReturnsNoDuplicate() {
        runTest {
            initRecommender { _, _, _ -> listOf(TestMocks.user1, TestMocks.user3) }
            val recommendations = recommender(TestMocks.user2)

            assertThat(recommendations.first(), containsInAnyOrder(Mocks.workLaFermeDesAnimaux))
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun recommenderRemovesInterestedWhenSpecified() {
        runTest {
            initRecommender { _, _, _ -> listOf(TestMocks.user1) }
            // User3 is interested in User1's La ferme des animaux
            assertEquals(
                emptyList<Work>(),
                recommender(TestMocks.user3, removeInterested = true).first()
            )
        }
    }

    @Test
    fun recommenderThrowsErrorIfUserIsUsedAsNeighbour() {
        initRecommender { _, _, _ -> listOf(TestMocks.user1) }
        assertThrows<IllegalStateException> {
            recommender(TestMocks.user1)
        }
    }

}