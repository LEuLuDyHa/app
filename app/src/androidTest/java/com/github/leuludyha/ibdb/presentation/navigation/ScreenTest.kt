package com.github.leuludyha.ibdb.presentation.navigation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenTest {

    @Test
    fun signInScreenHasCorrectRoute() =
        assertThat(Screen.SignIn.route).isEqualTo("sign_in_screen")

    @Test
    fun signUpScreenHasCorrectRoute() =
        assertThat(Screen.SignUp.route).isEqualTo("sign_up_screen")

    @Test
    fun profileScreenHasCorrectRoute() =
        assertThat(Screen.Profile.route).isEqualTo("profile_screen")

    @Test
    fun homeScreenHasCorrectRoute() =
        assertThat(Screen.Home.route).isEqualTo("home_screen")

    @Test
    fun bookSearchScreenHasCorrectRoute() =
        assertThat(Screen.BookSearch.route).isEqualTo("book_search_screen/{searchQuery}")

    @Test
    fun bookSearchScreenPassQueryHasCorrectRoute() =
        assertThat(Screen.BookSearch.passQuery("myQuery")).isEqualTo("book_search_screen/myQuery")

    @Test
    fun collectionScreenHasCorrectRoute() =
        assertThat(Screen.Collection.route).isEqualTo("collection")

    @Test
    fun barcodeScanScreenHasCorrectRoute() =
        assertThat(Screen.BarcodeScan.route).isEqualTo("barcode_scan")

    @Test
    fun bookDetailsScreenHasCorrectRoute() =
        assertThat(Screen.BookDetails.route).isEqualTo("book_details_screen/{bookId}")

    @Test
    fun bookDetailsScreenPassBookIdHasCorrectRoute() =
        assertThat(Screen.BookDetails.passBookId("bookId")).isEqualTo("book_details_screen/bookId")

    @Test
    fun authorDetailsScreenHasCorrectRoute() =
        assertThat(Screen.AuthorDetails.route).isEqualTo("author_details_screen/{authorId}")

    @Test
    fun authorDetailsScreenPassAuthorIdHasCorrectRoute() =
        assertThat(Screen.AuthorDetails.passAuthorId("authorId")).isEqualTo("author_details_screen/authorId")

    @Test
    fun findBookScreenHasCorrectRoute() =
        assertThat(Screen.FindBook.route).isEqualTo("find_book")

    @Test
    fun userProfileScreenHasCorrectRoute() =
        assertThat(Screen.UserProfile.route).isEqualTo("user_profile")

    @Test
    fun googleMapsScreenHasCorrectRoute() =
        assertThat(Screen.GoogleMaps.route).isEqualTo("maps")
}