package com.github.leuludyha.domain.model.recommandation


import com.github.leuludyha.domain.TestMocks
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.library.recommendation.RecommenderSystem
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.repository.UserRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class RecommenderSystemTest {

    lateinit var recommender: RecommenderSystem

    private fun initRecommender(
        f: (user: User, distance: (User, User) -> Float, n: Int) -> List<User>,
    ) {
        recommender = RecommenderSystem(
            object : UserRepository {
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
    fun recommenderReturnEmptyListOnNoUserInSystem() {
        initRecommender { _, _, _ -> listOf() }
        assertThat("Should not crash on empty system", recommender(TestMocks.user1).isEmpty())
    }

    @Test
    fun recommenderReturnsUserWorksOnOneUserInSystem() {
        initRecommender { _, _, _ -> listOf(TestMocks.user1) }
        val recommendations = recommender(TestMocks.user2)
        assertThat("Should not be empty", recommendations.isNotEmpty())
        assertEquals(
            TestMocks.user1.getWorksInReadingList().toSet(),
            recommendations.toSet()
        )
    }

    @Test
    fun recommenderReturnsNoDuplicate() {
        initRecommender { _, _, _ -> listOf(TestMocks.user1, TestMocks.user3) }
        val recommendations: List<Work> = recommender(TestMocks.user2)

        assertThat(recommendations, containsInAnyOrder(Mocks.workLaFermeDesAnimaux))
    }

    @Test
    fun recommenderRemovesInterestedWhenSpecified() {
        initRecommender { _, _, _ -> listOf(TestMocks.user1) }
        // User3 is interested in User1's La ferme des animaux
        assertEquals(emptyList<Work>(), recommender(TestMocks.user3, removeInterested = true))
    }

    @Test
    fun recommenderThrowsErrorIfUserIsUsedAsNeighbour() {
        initRecommender { _, _, _ -> listOf(TestMocks.user1) }
        assertThrows<IllegalStateException> {
            recommender(TestMocks.user1)
        }
    }

}