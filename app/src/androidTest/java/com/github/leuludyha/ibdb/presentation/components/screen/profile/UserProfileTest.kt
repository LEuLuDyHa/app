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
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.ibdb.presentation.screen.profile.UserProfile
import com.github.leuludyha.ibdb.presentation.screen.profile.UserProfileViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserProfileTest {

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    private val user = User("TestUser", UserPreferences())
    private val authContext = AuthenticationContext(user)
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
        val usernameNode = composeTestRule.onNode(hasText(user.username))
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