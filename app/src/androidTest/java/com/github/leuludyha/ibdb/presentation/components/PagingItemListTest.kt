package com.github.leuludyha.ibdb.presentation.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.utils.PagingItemList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PagingItemListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun oneWorkHasItsTitleDisplayedWhenVertical() {
        composeTestRule.setContent {
            PagingItemList(
                modifier = Modifier.wrapContentSize(),
                orientation = Orientation.Vertical,
                values = MockLibraryRepositoryImpl().searchRemotely("query").collectAsLazyPagingItems(),
            ) { Text(it.title ?: "Unknown") }
        }

        composeTestRule.waitForIdle()

        workMrFox.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
    }

    @Test
    fun oneWorkHasItsTitleDisplayedWhenHorizontal() {
        composeTestRule.setContent {
            PagingItemList(
                modifier = Modifier.wrapContentSize(),
                orientation = Orientation.Vertical,
                values = MockLibraryRepositoryImpl().searchRemotely("query").collectAsLazyPagingItems(),
            ) { Text(it.title ?: "Unknown") }
        }

        composeTestRule.waitForIdle()

        workMrFox.title?.let { composeTestRule.onNodeWithText(it, substring = true).assertExists() }
    }
}