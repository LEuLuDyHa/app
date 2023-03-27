package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RawAuthorLinksTest: RequiringLibraryApiTest() {

    @Test
    fun fieldsAreProperlyGotten() {
        val authorLinks = RawAuthorLinks(
            authorKey = "/authors/OL34184A",
            prevKey = "x",
            nextKey = "x"
        )

        assertThat(authorLinks.authorKey).isEqualTo( "/authors/OL34184A")
        assertThat(authorLinks.prevKey).isEqualTo( "x")
        assertThat(authorLinks.nextKey).isEqualTo( "x")
    }
}