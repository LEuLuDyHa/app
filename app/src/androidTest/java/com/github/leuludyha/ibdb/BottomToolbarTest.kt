package com.github.leuludyha.ibdb

import dagger.hilt.android.testing.HiltAndroidTest

@HiltAndroidTest
class BottomToolbarTest {

//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule(order = 1)
//    val composeTestRule = createComposeRule()
//
//    private lateinit var navController: NavHostController
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//
//        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current)
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            NavGraph(navController = navController)
//        }
//    }
//
//    @Test
//    fun mapsToolbarSlotOpensGoogleMapsComposable() {
//        composeTestRule
//            .onNodeWithTag("GoogleMaps::main")
//            .assertDoesNotExist()
//
//        composeTestRule
//            .clickOnBottomTab(TabDescriptor.Maps)
//
//        composeTestRule
//            .onNodeWithTag("GoogleMaps::main")
//            .assertExists()
//    }
//
//    @Test
//    fun searchToolbarSlotOpensSearchComposableWithSearchField() {
//        composeTestRule
//            .clickOnBottomTab(TabDescriptor.Search)
//
//        composeTestRule
//            .onNodeWithTag("book_search::search_field")
//            .assertExists()
//    }
//
//    @Test
//    fun searchToolbarSlotOpensSearchComposableWithScanButton() {
//        composeTestRule
//            .clickOnBottomTab(TabDescriptor.Search)
//
//        composeTestRule
//            .onNodeWithTag("book_search::barcode_scan_button")
//            .assertExists()
//    }
}
