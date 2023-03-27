package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RawWorkLinksTest: RequiringLibraryApiTest() {

    @Test
    fun `Fields are properly gotten`() {
        val authorLinks = RawWorkLinks(
            workKey = "/works/OL34184A",
            prevKey = "x",
            nextKey = "x"
        )

        assertThat(authorLinks.workKey).isEqualTo( "/works/OL34184A")
        assertThat(authorLinks.prevKey).isEqualTo( "x")
        assertThat(authorLinks.nextKey).isEqualTo( "x")
    }
}