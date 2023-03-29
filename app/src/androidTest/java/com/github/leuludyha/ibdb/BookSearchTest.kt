package com.github.leuludyha.ibdb

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.rule.GrantPermissionRule
import com.github.leuludyha.domain.model.library.*
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.SearchUseCase
import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
import com.github.leuludyha.ibdb.presentation.components.search.BookSearchViewModel
import com.github.leuludyha.ibdb.presentation.navigation.Screen
import com.github.leuludyha.ibdb.presentation.screen.search.barcode.BarcodeScreen
import com.github.leuludyha.ibdb.presentation.screen.search.barcode.BarcodeScreenViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookSearchTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.CAMERA)

    @Test
    fun barcodeScanningButtonOpensCorrespondingScreen() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            //The navController is needed to test that the correct window is opened,
            //so I provide the bare minimum needed for this to work.
            //Cannot instantiate the app's NavGraph since hilt cannot deal with the injections
            //without launching the whole app, which I want to avoid for an isolated test.
            NavHost(
                navController = navController,
                startDestination = Screen.BookSearch.route
            ) {
                composable(route = Screen.BookSearch.route) {
                    BookSearch(
                        navController, PaddingValues(), BookSearchViewModel(
                            SearchUseCase(LibraryRepositoryMock())
                        )
                    ) {
                        //Does nothing for this test
                    }
                }
                composable(route = Screen.BarcodeScan.route) {
                    BarcodeScreen(navController, BarcodeScreenViewModel())
                }
            }
        }

        //This call here looks super weird, and it is. For some reason, without it Cirrus' tests fail for some reason I don't understand,
        //for I am unable to replicate it in my computer and the screen recording looks just fine.
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("barcode_screen::camera_layout")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("book_search::barcode_scan_button")
            .performClick()

        composeTestRule
            .onNodeWithTag("barcode_screen::camera_layout")
            .assertExists()
    }

    @Test
    fun searchEffectivelyRetrievesOneWorkAfterSearching() {
        var result: List<Work>? = null

        composeTestRule.setContent {
            //No need for a (working) navigator in this test
            val navController = rememberNavController()

            BookSearch(
                navController = navController,
                outerPadding = PaddingValues(),
                viewModel = BookSearchViewModel(
                    SearchUseCase(LibraryRepositoryMock())
                )
            ) {
                result = it
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("book_search::search_field")
            .performTextInput("Blabla")

        composeTestRule
            .onNodeWithTag("book_search::search_field")
            .performImeAction()

        composeTestRule.waitUntil(timeoutMillis = 1000) {
            result != null
        }

        assertEquals(listOf(Mocks.work), result)
    }

    //I am not sure that this is a good test returning that Result.Loading doesn't look ideal to me
    @Test
    fun queryLoadingIndicatorIsDisplayed() {
        composeTestRule.setContent {
            //No need for a (working) navigator in this test
            val navController = rememberNavController()

            BookSearch(
                navController = navController,
                outerPadding = PaddingValues(),
                viewModel = BookSearchViewModel(
                    SearchUseCase(LibraryRepositoryMock {
                        return@LibraryRepositoryMock Result.Loading()
                    })

                )
            ) {
                //Does nothing for this test
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("book_search::query_loading_indicator")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("book_search::search_field")
            .performTextInput("Blabla")

        composeTestRule
            .onNodeWithTag("book_search::search_field")
            .performImeAction()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("book_search::query_loading_indicator")
            .assertExists()
    }
}

/**
 * This mock class is created to control the kind of requests the view model will make.
 */
private class LibraryRepositoryMock(val searchReturn: () -> Result<List<Work>> = { Result.Success(listOf(Mocks.work)) }) : LibraryRepository {
    override suspend fun search(query: String): Result<List<Work>> {
        return searchReturn.invoke()
    }

    override suspend fun workById(workId: String): Result<Work> {
        return Result.Success(Mocks.work)
    }

    override suspend fun worksByAuthorId(authorId: String): Result<List<Work>> {
        return Result.Success(listOf(Mocks.work))
    }

    override suspend fun editionsByWorkId(workId: String): Result<List<Edition>> {
        return Result.Error("Couldn't retrieve any edition")
    }

    override suspend fun editionById(editionId: String): Result<Edition> {
        return Result.Error("Couldn't retrieve any edition")
    }

    override suspend fun editionByISBN(isbn: Long): Result<Edition> {
        return Result.Error("Couldn't retrieve any edition")
    }

    override suspend fun authorById(authorId: String): Result<Author> {
        return Result.Success(Mocks.author)
    }
}
