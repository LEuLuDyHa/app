package com.github.leuludyha.ibdb
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.test.performClick
//import androidx.navigation.testing.TestNavHostController
//import com.github.leuludyha.domain.model.library.Mocks
//import com.github.leuludyha.domain.model.library.Work
//import com.github.leuludyha.ibdb.presentation.components.search.BookSearch
//import com.github.leuludyha.ibdb.presentation.components.search.BookSearchViewModel
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import javax.inject.Inject
//
//@HiltAndroidTest
//class BookSearchScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    lateinit var bookSearchViewModel : BookSearchViewModel
//
//    @Before
//    fun init() {
//        hiltRule.inject()
//    }
//
//    @Test
//    fun barcodeScanningButtonOpensCorrespondingScreen() {
//        composeTestRule.setContent {
//            val navController = TestNavHostController(LocalContext.current)
//            BookSearch(
//                navController = navController,
//                outerPadding = PaddingValues(),
//                viewModel =  bookSearchViewModel
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
