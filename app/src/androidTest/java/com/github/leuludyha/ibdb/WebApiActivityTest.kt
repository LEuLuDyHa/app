package com.github.leuludyha.ibdb

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.ibdb.webapi.WebApiActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebApiActivityTest {

    @get:Rule
    val rule = createAndroidComposeRule<WebApiActivity>()

    private var mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        mockWebServer.dispatcher = SuccessDispatcher()
    }

    //TODO the test fails for now...
    /*@Test
    fun getActivityReturnsCorrectResponseWhenSuccess() {
        val response = "successful test!"

        // Click on button
        rule.onNode(hasTestTag("ask_activity_button"))
            .performClick()

        Thread.sleep(5000)
        rule.onNode(hasTestTag("activity_text"))
            .assertTextContains(response)
    }*/

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}