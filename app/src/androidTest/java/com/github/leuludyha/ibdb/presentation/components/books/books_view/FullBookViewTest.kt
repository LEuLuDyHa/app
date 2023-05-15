package com.github.leuludyha.ibdb.presentation.components.books.books_view

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.DeleteWorkPrefLocallyUseCase
import com.github.leuludyha.domain.useCase.GetAllWorkPrefsLocallyUseCase
import com.github.leuludyha.domain.useCase.SaveWorkPrefLocallyUseCase
import com.github.leuludyha.ibdb.presentation.components.books.book_views.FullBookView
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.ReadingStateControlViewModel
import com.github.leuludyha.ibdb.presentation.components.books.reading_list.controls.TestTags
import kotlinx.coroutines.flow.flowOf
import onNodeByTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FullBookViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var readingStateControlViewModel: ReadingStateControlViewModel
    private lateinit var repository: LibraryRepository

    @Before
    fun setup() {
        repository = MockLibraryRepositoryImpl()
        readingStateControlViewModel = ReadingStateControlViewModel(
            GetAllWorkPrefsLocallyUseCase(repository),
            SaveWorkPrefLocallyUseCase(repository),
            DeleteWorkPrefLocallyUseCase(repository)
        )
    }

    @Test
    fun titleIsDisplayedOnNonNullTitle() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox
            )
        }

        composeTestRule.onNodeWithText(workMrFox.title!!).assertExists()
    }

    @Test
    fun titleIsNotDisplayedOnNullTitle() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(title = null)
            )
        }

        composeTestRule.onNodeWithText(workMrFox.title!!).assertDoesNotExist()
    }

    @Test
    fun likeButtonIsPresent() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox
            )
        }

        composeTestRule.onNodeByTag(TestTags.likeButton).assertExists()
    }

    @Test
    fun authorLabelIsAuthorWhen1Author() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox
            )
        }

        composeTestRule.onNodeWithText("Author: ").assertExists()
    }

    @Test
    fun authorLabelIsAuthorsWhenMoreThan1Author() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(authors = flowOf(listOf(
                    authorRoaldDahl,
                    authorGeorgeOrwell
                )))
            )
        }

        composeTestRule.onNodeWithText("Authors: ").assertExists()
    }

    @Test
    fun authorLabelIsEmptyWhenNoAuthor() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(authors = flowOf(listOf()))
            )
        }

        composeTestRule.onNodeWithText("Authors: ").assertDoesNotExist()
        composeTestRule.onNodeWithText("Author: ").assertDoesNotExist()
    }

    @Test
    fun authorNameIsPresentOn1Author() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox
            )
        }

        composeTestRule.onNodeWithText(authorRoaldDahl.name!!).assertExists()
    }

    @Test
    fun authorNameIsNotPresentOnNullAuthorName() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(authors = flowOf(listOf(authorRoaldDahl.copy(name = null))))
            )
        }

        composeTestRule
            .onNodeWithTag("mini_author_view::author_name", useUnmergedTree = true)
            .assertTextEquals("")
    }

    @Test
    fun subjectsLabelIsSubjectWhen1Subject() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(subjects = flowOf(listOf("subject")))
            )
        }

        composeTestRule.onNodeWithText("Subject: ").assertExists()
    }

    @Test
    fun subjectsLabelIsSubjectWhenMultipleSubjects() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(subjects = flowOf(listOf("subject1", "subject2")))
            )
        }

        composeTestRule.onNodeWithText("Subjects: ").assertExists()
    }

    @Test
    fun subjectLabelIsEmptyWhenNoSubject() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(subjects = flowOf(listOf()))
            )
        }

        composeTestRule.onNodeWithText("Subjects: ").assertDoesNotExist()
        composeTestRule.onNodeWithText("Subject: ").assertDoesNotExist()
    }

    @Test
    fun subjectIsPresentOn1Subject() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(subjects = flowOf(listOf("subject")))
            )
        }

        composeTestRule.onNodeWithText("subject").assertExists()
    }

    @Test
    fun fourthSubjectIsNotPresentOn4Subjects() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox.copy(subjects = flowOf(listOf("s1", "s2", "s3", "s4")))
            )
        }

        composeTestRule.onNodeWithText("s4").assertDoesNotExist()
    }

    @Test
    fun bookCoverIsPresentOnNonNullCover() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox
            )
        }

        composeTestRule
            .onNodeWithTag("full_book_view::book_cover")
            .assertExists()
    }

    @Test
    fun bookCoverIsPresentOnNullCover() {
        composeTestRule.setContent {
            FullBookView(
                readingStateControlViewModel = readingStateControlViewModel,
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()),
                work = workMrFox
            )
        }

        composeTestRule
            .onNodeWithTag("full_book_view::book_cover")
            .assertExists()
    }
}