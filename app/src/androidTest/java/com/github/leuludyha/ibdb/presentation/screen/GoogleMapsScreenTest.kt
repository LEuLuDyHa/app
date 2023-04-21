package com.github.leuludyha.ibdb.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.repository.UserRepository
import com.github.leuludyha.domain.useCase.users.GetNearbyUsersUseCase
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreen
import com.github.leuludyha.ibdb.presentation.screen.maps.GoogleMapsScreenViewModel
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CompletableFuture

class GoogleMapsScreenTest {

    //These tests are rather limited and no marker is properly tested.
    //I can't seem to find any way of testing any markers or anything inside the
    //GoogleMap composable, so I can just check what is not inside it (barely anything)

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    //The useCase can be used on multiple test but careful! The viewModels have to be recreated for each test!
    //They cannot be shared
    private val injectedUseCase: GetNearbyUsersUseCase = GetNearbyUsersUseCase(UserRepositoryMock())

    @Test
    fun clickingOnLocationButtonDoesNotCrash() {
        //It doesn't look like there is any way of accessing markers from the tests,
        //so no way of checking that markers appeared or anything like that.
        composeTestRule.setContent {
            GoogleMapsScreen(
                navController = rememberNavController(),
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel(injectedUseCase)
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("GoogleMaps::location_button")
            .performClick()

        composeTestRule.waitForIdle()
    }

    @Test
    fun clickingOnRefreshButtonDoesNotCrash() {
        composeTestRule.setContent {
            GoogleMapsScreen(
                navController = rememberNavController(),
                paddingValues = PaddingValues(0.dp),
                viewModel = GoogleMapsScreenViewModel(injectedUseCase)
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("GoogleMaps::refresh_button")
            .performClick()

        composeTestRule.waitForIdle()
    }

    @Test
    fun clickingOnRefreshButtonIncreasesOrMaintainsNumberOfMarkers() {
        val viewModel = GoogleMapsScreenViewModel(GetNearbyUsersUseCase(UserRepositoryMock()))

        composeTestRule.setContent {
            GoogleMapsScreen(
                navController = rememberNavController(),
                paddingValues = PaddingValues(0.dp),
                viewModel = viewModel
            )
        }

        //This is to wait for the map to load
        composeTestRule.waitForIdle()

//        composeTestRule.onRoot(true).printToLog("Debug", maxDepth = 10)

        val oldNumberOfNearbyUsers = viewModel.nearbyUsers.value

        composeTestRule
            .onNodeWithTag("GoogleMaps::refresh_button")
            .performClick()

        composeTestRule.waitForIdle()

        assert(oldNumberOfNearbyUsers.size <= viewModel.nearbyUsers.value.size)
    }
}

private class UserRepositoryMock: UserRepository {
    override fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User> {
        return CompletableFuture.completedFuture(Mocks.mainUser)
    }

    override fun getNearbyUsers(
        latitudeMax: Double,
        longitudeMax: Double,
        latitudeMin: Double,
        longitudeMin: Double
    ): CompletableFuture<List<User>> {
        return CompletableFuture.completedFuture(Mocks.userList)
    }

    override fun getNeighbouringUsersOf(
        user: User,
        distance: (User, User) -> Float,
        n: Int
    ): List<User> {
        return Mocks.userList
    }

}
