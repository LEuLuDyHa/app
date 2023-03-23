package com.github.leuludyha.domain.model

import java.io.Serializable

data class Cover(
    val id: Long
) : Serializable {
    fun urlForSize(coverSize: CoverSize) =
        "https://covers.openlibrary.org/b/id/${id}-${coverSize}.jpg"
}
