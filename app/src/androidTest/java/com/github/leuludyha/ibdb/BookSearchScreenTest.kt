package com.github.leuludyha.ibdb

//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.test.performClick
//import androidx.navigation.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.createGraph
//import com.github.leuludyha.domain.model.library.Author
//import com.github.leuludyha.domain.model.library.Edition
//import com.github.leuludyha.domain.model.library.Result
//import com.github.leuludyha.domain.model.library.Work
//import com.github.leuludyha.domain.repository.LibraryRepository
//import com.github.leuludyha.domain.useCase.SearchUseCase
//import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
//import com.github.leuludyha.ibdb.presentation.components.search.BookSearchViewModel
//import com.github.leuludyha.ibdb.presentation.navigation.Screen
//import com.github.leuludyha.ibdb.presentation.screen.search.BookSearchScreen
//import com.github.leuludyha.ibdb.presentation.screen.search.barcode.BarcodeScreen
//import org.junit.Rule
//import org.junit.Test
//
//class BookSearchScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun barcodeScanningButtonOpensCorrespondingScreen() {
//        composeTestRule.setContent {
//            val navController = rememberNavController()
//            navController.createGraph(Screen.BookSearch.route) {
//                composable(route = Screen.BookSearch.route) {
//                    BookSearchScreen(navController, PaddingValues())
//                }
//                composable(route = Screen.BarcodeScan.route) {
//                    BarcodeScreen(navController)
//                }
//            }
//            BookSearch(
//                navController = navController,
//                outerPadding = PaddingValues(),
//                viewModel =  BookSearchViewModel(
//                    SearchUseCase(LibraryRepositoryMock())
//                )
//            ) {
//
//            }
//        }
//
//        composeTestRule
//            .onNodeWithTag("barcode_screen::camera_layout")
//            .assertDoesNotExist()
//
//        composeTestRule
//            .onNodeWithTag("book_search::barcode_scan_button")
//            .performClick()
//
//        composeTestRule
//            .onNodeWithTag("barcode_screen::camera_layout")
//            .assertExists()
//    }
//}
//
//private class LibraryRepositoryMock : LibraryRepository {
//    override suspend fun search(query: String): Result<List<Work>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun workById(workId: String): Result<Work> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun worksByAuthorId(authorId: String): Result<List<Work>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun editionsByWorkId(workId: String): Result<List<Edition>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun editionById(editionId: String): Result<Edition> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun editionByISBN(isbn: Long): Result<Edition> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun authorById(authorId: String): Result<Author> {
//        TODO("Not yet implemented")
//    }
//}
