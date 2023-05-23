package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.authentication.Endpoint
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.ibdb.MockNearbyConnection
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedWorkListenerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sharedWorkListenerDoesNotCrash() {
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

    @Test
    fun packetProcessorShowsShareWorkAlertDialog() {
        val endPoint = Endpoint("name", "id")
        composeTestRule.setContent {
            PacketProcessor(
                endpoint = endPoint,
                packet = NearbyMsgPacket(NearbyMsgPacket.ShareWork, "workId"),
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext),
                onProcessed = { }
            )
        }

        composeTestRule.onNodeWithText("Accept ${endPoint.name}'s sharing")
        composeTestRule.onNodeWithText("${endPoint.name} wants to share a book with you")
        composeTestRule.onNodeWithText("Accept")
        composeTestRule.onNodeWithText("Refuse")
    }

    @Test
    fun packetProcessorShareWorkClickOnRefuseCallsOnProcessed() {
        val endPoint = Endpoint("name", "id")
        var clicked = false
        composeTestRule.setContent {
            PacketProcessor(
                endpoint = endPoint,
                packet = NearbyMsgPacket(NearbyMsgPacket.ShareWork, "workId"),
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext),
                onProcessed = { clicked = true }
            )
        }

        composeTestRule.onNodeWithText("Refuse").performClick()
        Truth.assertThat(clicked).isTrue()
    }

    @Test
    fun packetProcessorShowsAddFriendAlertDialog() {
        val endPoint = Endpoint("name", "id")
        composeTestRule.setContent {
            PacketProcessor(
                endpoint = endPoint,
                packet = NearbyMsgPacket(NearbyMsgPacket.AddFriend, "workId"),
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext),
                onProcessed = { }
            )
        }

        composeTestRule.onNodeWithText("Accept ${endPoint.name}'s friend request")
        composeTestRule.onNodeWithText("${endPoint.name} wants to add you as their friend")
        composeTestRule.onNodeWithText("Accept")
        composeTestRule.onNodeWithText("Refuse")
    }

    @Test
    fun packetProcessorAddFriendClickOnRefuseCallsOnProcessed() {
        val endPoint = Endpoint("name", "id")
        var clicked = false
        composeTestRule.setContent {
            PacketProcessor(
                endpoint = endPoint,
                packet = NearbyMsgPacket(NearbyMsgPacket.AddFriend, "workId"),
                navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext),
                onProcessed = { clicked = true }
            )
        }

        composeTestRule.onNodeWithText("Refuse").performClick()
        Truth.assertThat(clicked).isTrue()
    }

    @Test
    fun spinningProgressBarIsNotCrashing() {
        composeTestRule.setContent {
            SpinningProgressBar()
        }

        composeTestRule.waitForIdle()
    }

}
