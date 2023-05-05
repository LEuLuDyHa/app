package com.github.leuludyha.ibdb.presentation.components.books.recommendations

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.recommendation.RecommenderSystem
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.UserRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID
import java.util.concurrent.CompletableFuture

@RunWith(AndroidJUnit4::class)
class RecommendationListTest {

    private val user1: User = MainUser(
        UUID.randomUUID().toString(),
        "Camilla", "",
        null,
        UserPreferences(),
        flowOf(mutableMapOf(
            Pair(
                Mocks.workLaFermeDesAnimaux.id, WorkPreference(
                    Mocks.workLaFermeDesAnimaux, WorkPreference.ReadingState.FINISHED, true
                )
            )
        )),
        UserStatistics(
            preferredSubjects = listOf("Fantasy", "Historical", "Political Science"),
            preferredAuthors = listOf(),
            preferredWorks = listOf(Mocks.workLaFermeDesAnimaux),
            averageNumberOfPages = 256,
        ),
        friends = listOf(),
        latitude = 0.0,
        longitude = 0.0
    )

    private val user2: MainUser = MainUser(
        UUID.randomUUID().toString(),
        "Hector", "",
        null,
        UserPreferences(),
        flowOf(mutableMapOf()),
        UserStatistics(
            preferredSubjects = listOf(),
            preferredAuthors = listOf(Mocks.authorGeorgeOrwell),
            preferredWorks = listOf(Mocks.work1984),
            averageNumberOfPages = 14,
        ),
        friends = listOf(user1),
        latitude = 0.0,
        longitude = 0.0
    )

    private val recommender = RecommenderSystem(
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
                return listOf(user1)
            }
        }
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun itemListExists() {
        composeTestRule.setContent {
            RecommendationList(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                viewModel = RecommendationListViewModel(
                    recommender,
                    AuthenticationContext(user2)
                ),
                onRecommendations = { }
            )
        }

        composeTestRule.onNodeWithTag("item_list", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun recommendedWorkTitleIsNotDisplayedWhenNoWorkRecommended() {
        composeTestRule.setContent {
            RecommendationList(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                viewModel = RecommendationListViewModel(
                    recommender,
                    AuthenticationContext(Mocks.mainUser)
                ),
                onRecommendations = { }
            )
        }

        composeTestRule.onNodeWithTag("mini_book_view", useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun recommendedWorkTitleIsDisplayedWhenWorkRecommended() {
        composeTestRule.setContent {
            RecommendationList(
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().context),
                viewModel = RecommendationListViewModel(
                    recommender,
                    AuthenticationContext(user2)
                ),
                onRecommendations = { }
            )
        }

        composeTestRule.onNodeWithText(Mocks.workLaFermeDesAnimaux.title.toString(), useUnmergedTree = true)
            .assertExists()
    }
}