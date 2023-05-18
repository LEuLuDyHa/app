package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.ibdb.MockNearbyConnection
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedWorkListenerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sharedWorkListenerDoesNotCrashWithoutPermissions() {
        composeTestRule.setContent {
            SharedWorkListener(
                navController = rememberNavController(),
                viewModel = SharedWorkListenerViewModel(
                    authContext = AuthenticationContext(Mocks.mainUser, MockNearbyConnection())
                )
            )
        }

        composeTestRule.waitForIdle()
    }
}
