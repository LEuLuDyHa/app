package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ApiHelperTest {
    @Test
    fun extractIdFromKeyReturnsCorrectIdOnCorrectKey() {
        val id = extractIdFromKey("/works/OL45804W", "/works/")
        assertThat(id).isNotNull()
        assertThat(id).isEqualTo("OL45804W")
    }

    @Test
    fun extractIdFromKeyReturnsNullOnIncorrectKey() {
        val id = extractIdFromKey("/works/OL45804W", "wrong")
        assertThat(id).isNull()
    }
}