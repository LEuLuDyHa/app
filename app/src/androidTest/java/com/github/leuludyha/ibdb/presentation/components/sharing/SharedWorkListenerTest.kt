package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.SaveWorkLocallyUseCase
import com.github.leuludyha.ibdb.MockNearbyConnection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedWorkListenerTest {

    lateinit var libraryRepository: LibraryRepository

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() = runTest {
        libraryRepository = MockLibraryRepositoryImpl()
    }

    @Test
    fun sharedWorkListenerDoesNotCrashWithoutPermissions() {
        composeTestRule.setContent {
            SharedWorkListener(
                navController = rememberNavController(),
                viewModel = SharedWorkListenerViewModel(
                    authContext = AuthenticationContext(Mocks.mainUser, MockNearbyConnection()),
                    saveWorkLocallyUseCase = SaveWorkLocallyUseCase(libraryRepository)
                )
            )
        }

        composeTestRule.waitForIdle()
    }
}
