package com.github.leuludyha.domain

import com.github.leuludyha.domain.model.Document
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class DocumentTest {

    @Test
    fun `String representation of a document with 1 author is '{title}, by {author}'`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
        )

        assertThat(doc.toString()).isEqualTo("Awesome book, by Awesome guy")
    }

    @Test
    fun `String representation of a document with 2 authors is '{title}, by {author1 and author2}'`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy", "Less awesome guy"),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
        )

        assertThat(doc.toString()).isEqualTo("Awesome book, by Awesome guy and Less awesome guy")
    }

    @Test
    fun `String representation of a document with 3 authors is '{title}, by {author1, author2 and author3}'`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("1", "2", "3"),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
        )

        assertThat(doc.toString()).isEqualTo("Awesome book, by 1, 2 and 3")
    }

    @Test
    fun `String representation of a document with null authors list is '{title}, by an unknown author'`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = null,
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
        )

        assertThat(doc.toString()).isEqualTo("Awesome book, by an unknown author")
    }

    @Test
    fun `String representation of a document with 0 author is '{title}, by an unknown author'`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf(),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
        )

        assertThat(doc.toString()).isEqualTo("Awesome book, by an unknown author")
    }

    @Test
    fun `String representation of a document with null title is 'Unknown title, by {authors}'`() {
        val doc = Document(
            coverId = 0,
            title = null,
            authorNames = listOf(),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
        )

        assertThat(doc.toString()).isEqualTo("Unknown title, by an unknown author")
    }

}