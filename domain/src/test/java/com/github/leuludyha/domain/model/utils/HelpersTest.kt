package com.github.leuludyha.domain.model.utils

import com.github.leuludyha.domain.util.toText
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.Matchers.`is` as Is


class HelpersTest {

    @Test
    fun listToTextDisplaysEmptyStringOnEmpty() {
        assertThat(listOf<String>().toText(), Is(""))
    }

    @Test
    fun listToTextDisplaysItemToStringOnSingleItem() {
        val name = "Billy"
        assertThat(listOf(name).toText(), Is(name))
    }

    @Test
    fun listToTextDisplaysItemsSeparatedByAndOnTwoItems() {
        val name1 = "Billy"
        val name2 = "Bobby"
        assertThat(listOf(name1, name2).toText(), Is("$name1 and $name2"))
    }

    @Test
    fun listToTextDisplaysItemsSeparatedByCommaAndLastItemWithAndOnFourItems() {
        val name1 = "Billy"
        val name2 = "Bobby"
        val name3 = "Valery"
        val name4 = "Cathia"
        assertThat(
            listOf(name1, name2, name3, name4).toText(),
            Is("$name1, $name2, $name3 and $name4")
        )
    }
}