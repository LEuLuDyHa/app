package com.github.leuludyha.data.api

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class DocumentTest {

    @Test
    fun `Properties of a document are properly gotten`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
            editionKeys = listOf("key")
        )

        assertThat(doc.coverId).isEqualTo(0)
        assertThat(doc.title).isEqualTo("Awesome book")
        assertThat(doc.authorNames).isEqualTo(listOf("Awesome guy"))
        assertThat(doc.firstPublishYear).isEqualTo(0)
        assertThat(doc.key).isEqualTo("Awesome key")
        assertThat(doc.authorKeys).isEqualTo(listOf("Weird key"))
        assertThat(doc.editionKeys).isEqualTo(listOf("key"))
    }


    @Test
    fun `String representation of a document with 1 author is '{title}, by {author}'`() {
        val doc = Document(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "Awesome key",
            authorKeys = listOf("Weird key"),
            editionKeys = listOf("key")
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
            editionKeys = listOf("key")
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
            editionKeys = listOf("key")
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
            editionKeys = listOf("key")
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
            editionKeys = listOf("key")
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
            editionKeys = listOf("key")
        )

        assertThat(doc.toString()).isEqualTo("Unknown title, by an unknown author")
    }

}