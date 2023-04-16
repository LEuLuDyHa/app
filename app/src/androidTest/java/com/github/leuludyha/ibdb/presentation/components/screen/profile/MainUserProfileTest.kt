package com.github.leuludyha.ibdb.presentation.components.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.ibdb.presentation.screen.profile.UserProfile
import com.github.leuludyha.ibdb.presentation.screen.profile.UserProfileViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class MainUserProfileTest {

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mainUser = MainUser(
        UUID.randomUUID().toString(),
        "TestUser",
        null,
        "",
        UserPreferences(), UserStatistics(
            preferredWorks = listOf(),
            preferredSubjects = listOf(),
            preferredAuthors = listOf(),
            averageNumberOfPages = 0
        ), friends = listOf()
    )
    private val authContext = AuthenticationContext(mainUser)
    private val viewModel = UserProfileViewModel(authContext)

    @Test
    fun userProfileDisplaysProfilePicture() {

        composeTestRule.setContent {
            UserProfile(
                navController = navController,
                outerPadding = PaddingValues(),
                viewModel = viewModel
            )
        }

        // Verify that the username and profile picture are displayed correctly
        val usernameNode = composeTestRule.onNode(hasText(mainUser.username))
        val profilePictureNode = composeTestRule.onNode(hasContentDescription("Profile Picture"))

        usernameNode.assertIsDisplayed()
        profilePictureNode.assertIsDisplayed()
    }

    @Test
    fun userProfile_displaysButtons() {
        composeTestRule.setContent {
            UserProfile(
                navController = navController,
                outerPadding = PaddingValues(),
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("My Friends").assertIsDisplayed()
        composeTestRule.onNodeWithText("Something").assertIsDisplayed()
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }
}