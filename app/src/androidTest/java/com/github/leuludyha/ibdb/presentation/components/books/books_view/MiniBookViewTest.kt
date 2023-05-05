package com.github.leuludyha.ibdb.presentation.components.books.books_view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.util.toText
import com.github.leuludyha.ibdb.presentation.Orientation
import com.github.leuludyha.ibdb.presentation.components.books.book_views.MiniBookView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MiniBookViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun thumbnailImageExistsOnNonEmptyCoversVertical() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithTag("thumbnail", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun thumbnailImageExistsOnEmptyCoversVertical() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox.copy(covers = flowOf()),
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithTag("thumbnail", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun thumbnailImageDoesNotExistOnEmptyCoversHorizontal() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox.copy(covers = flowOf()),
                onClick = { },
                orientation = Orientation.Horizontal,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithTag("thumbnail", useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun thumbnailImageExistsOnNonEmptyCoversHorizontal() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Horizontal,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithTag("thumbnail", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun titleTextExistsOnCorrectTitleVertical() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithText(workMrFox.title.orEmpty(), useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun titleTextExistsOnNullTitleVertical() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox.copy(title = null),
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithText(workMrFox.title.orEmpty(), useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun titleTextExistsOnCorrectTitleHorizontal() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Horizontal,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithText(workMrFox.title.orEmpty(), useUnmergedTree = true)
            .assertExists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun authorTextExistsOnNonEmptyAuthorsVertical() = runTest {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithText(workMrFox.authors.first().toText(), useUnmergedTree = true)
            .assertExists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun authorTextExistsOnNonEmptyAuthorsHorizontal() = runTest {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Horizontal,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithText(workMrFox.authors.first().toText(), useUnmergedTree = true)
            .assertExists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun subjectsTextExistsOnNonEmptySubjectsWhenDisplaySubjectsTrueHorizontal() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Horizontal,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithTag("subject_list", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun subjectsTextExistsOnNonEmptySubjectsWhenDisplaySubjectsTrueVertical() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = true
            )
        }

        composeTestRule.onNodeWithTag("subject_list", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun subjectsTextExistsOnNonEmptySubjectsWhenDisplaySubjectsFalseVertical() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Vertical,
                footer = null,
                displaySubjects = false
            )
        }

        composeTestRule.onNodeWithTag("subject_list", useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun subjectsTextExistsOnNonEmptySubjectsWhenDisplaySubjectsFalseHorizontal() {
        composeTestRule.setContent {
            MiniBookView(
                work = workMrFox,
                onClick = { },
                orientation = Orientation.Horizontal,
                footer = null,
                displaySubjects = false
            )
        }

        composeTestRule.onNodeWithTag("subject_list", useUnmergedTree = true)
            .assertDoesNotExist()
    }
}
