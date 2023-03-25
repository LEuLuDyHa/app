package com.github.leuludyha.domain.model

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.formatListToText
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class AuthorTest {

    private val testAuthor1 = Author(
        null,
        "John Mockentosh",
        "cool-id",
        null, null, null
    )

    private val testAuthor2 = Author(
        null,
        "Stephany FamousWriter",
        "cool-id",
        null, null, null
    )

    @Test
    fun formatListToTextReturnsArtistNameOnSingleArtistList() {
        assertEquals(testAuthor1.name, formatListToText(listOf(testAuthor1)))
    }

    @Test
    fun formatListToTextReturnsArtistNamesSeparatedByAndOnTwoArtistList() {
        assertEquals(
            "${testAuthor1.name} and ${testAuthor2.name}",
            formatListToText(
                listOf(
                    testAuthor1, testAuthor2
                )
            )
        )
    }

    @Test
    fun formatListToTextReturnsArtistNamesSeparatedByAndAndColonOnThreeArtistList() {
        assertEquals(
            "${testAuthor1.name}, ${testAuthor2.name} and ${testAuthor1.name}",
            formatListToText(
                listOf(
                    testAuthor1, testAuthor2, testAuthor1
                )
            )
        )
    }
}