package com.github.leuludyha.ibdb.presentation.components.sharing

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.authentication.ConnectionLifecycleHandler
import com.github.leuludyha.domain.model.authentication.NearbyConnection
import com.github.leuludyha.domain.model.authentication.NearbyMsgPacket
import com.github.leuludyha.domain.model.library.Mocks
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
                    AuthenticationContext(
                        Mocks.mainUser,
                        MockNearbyConnection()
                    )
                )
            )
        }

        composeTestRule.waitForIdle()
    }

    @Test
    fun sharedWorkListenerDoesNotCrashWithPermissions() {

    }
}

private class MockNearbyConnection : NearbyConnection {
    var advertising: Boolean = false
    var discovering: Boolean = false
    var isConnected: Boolean = false

    override fun startAdvertising() {
        advertising = true
    }

    override fun stopDiscovery() {
        discovering = false
    }

    override fun isDiscovering(): Boolean {
        return discovering
    }

    override fun startDiscovery() {
        discovering = true
    }

    override fun stopAdvertising() {
        advertising = false
    }

    override fun isAdvertising(): Boolean {
        return advertising
    }

    override fun requestConnection(endpointId: String) {
        isConnected = true
    }

    override fun getDiscoveredEndpointIds(): List<String> {
        return listOf("MockConnection")
    }

    override fun sendPacket(packet: NearbyMsgPacket) {
        //Nothing
    }

    override fun disconnect() {
        isConnected = false
    }

    override fun addListener(handler: ConnectionLifecycleHandler) {
        //TODO: Add a few tests here if wanted
    }

    override fun removeListener(handler: ConnectionLifecycleHandler) {
        //Nothing
    }

    override fun isConnected(): Boolean {
        return isConnected
    }

}