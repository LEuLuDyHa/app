package com.github.leuludyha.ibdb.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object BookSearch : Screen("book_search_screen")
    object Collection : Screen("collection")
    object BarcodeScan : Screen("barcode_scan")
    object BookDetails : Screen("book_details_screen/{bookId}") {
        fun passBookId(bookId: String) = "book_details_screen/$bookId"
    }
    object FindBook : Screen("find_book")
    object GoogleMaps : Screen("maps")
}
