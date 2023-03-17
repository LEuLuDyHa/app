package com.github.leuludyha.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable;

data class Cover(
    @SerializedName("id")
    val id: Long
): Serializable {
    fun urlForSize(coverSize: CoverSize) =
        "https://covers.openlibrary.org/b/id/${id}-${coverSize}.jpg"
}
