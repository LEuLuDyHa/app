package com.github.leuludyha.domain.model.authentication

import com.google.common.truth.Truth
import org.junit.Test

class EndpointTest {
    @Test
    fun fieldsAreProperlyAccessed() {
        val ep = Endpoint("name", "id")

        Truth.assertThat(ep.name).isEqualTo("name")
        Truth.assertThat(ep.id).isEqualTo("id")
        Truth.assertThat(ep.Id()).isEqualTo(ep.id)
    }
}
