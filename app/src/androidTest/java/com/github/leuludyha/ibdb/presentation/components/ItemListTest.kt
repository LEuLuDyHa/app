package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.ibdb.presentation.Orientation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun oneWorkHasItsTitleDisplayedWhenVertical() {
        composeTestRule.setContent {
            ItemList(
                modifier = Modifier.wrapContentSize(),
                orientation = Orientation.Vertical,
                values = listOf(workMrFox),
            ) { Text(it.title ?: "Unknown") }
        }

        composeTestRule.waitForIdle()

        workMrFox.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
    }

    @Test
    fun oneWorkHasItsTitleDisplayedWhenHorizontal() {
        composeTestRule.setContent {
            ItemList(
                modifier = Modifier.wrapContentSize(),
                orientation = Orientation.Horizontal,
                values = listOf(workMrFox),
            ) { Text(it.title ?: "Unknown") }
        }

        composeTestRule.waitForIdle()

        workMrFox.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
    }

    @Test
    fun twoWorksHaveBothTheirTitleDisplayedWhenVertical() {
        composeTestRule.setContent {
            ItemList(
                modifier = Modifier.wrapContentSize(),
                orientation = Orientation.Vertical,
                values = listOf(workMrFox, work1984),
            ) { Text(it.title ?: "Unknown") }
        }

        composeTestRule.waitForIdle()

        workMrFox.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
        work1984.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
    }

    @Test
    fun twoWorksHaveBothTheirTitleDisplayedWhenHorizontal() {
        composeTestRule.setContent {
            ItemList(
                modifier = Modifier.wrapContentSize(),
                orientation = Orientation.Horizontal,
                values = listOf(workMrFox, work1984),
            ) { Text(it.title ?: "Unknown") }
        }

        composeTestRule.waitForIdle()

        workMrFox.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
        work1984.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
    }
}