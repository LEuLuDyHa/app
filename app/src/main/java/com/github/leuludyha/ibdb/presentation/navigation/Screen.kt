package com.github.leuludyha.ibdb.presentation.navigation

sealed class Screen(val route: String) {
    object SignIn : Screen("sign_in_screen")
    object SignUp : Screen("sign_up_screen")
    object Profile : Screen("profile_screen")
    object Home : Screen("home_screen")
    object BookSearch : Screen("book_search_screen/{searchQuery}") {
        fun passQuery(query: String) = "book_search_screen/$query"
    }

    object Collection : Screen("collection")
    object BarcodeScan : Screen("barcode_scan")
    object BookDetails : Screen("book_details_screen/{bookId}") {
        fun passBookId(bookId: String) = "book_details_screen/$bookId"
    }

    object AuthorDetails : Screen("author_details_screen/{authorId}") {
        fun passAuthorId(authorId: String) = "author_details_screen/$authorId"
    }

    object FindBook : Screen("find_book")
    object UserProfile : Screen("user_profile")
    object GoogleMaps : Screen("maps")
    object Share : Screen("share/{bookId}") {
        fun shareBookId(bookId: String) = "share/$bookId"
    }
}