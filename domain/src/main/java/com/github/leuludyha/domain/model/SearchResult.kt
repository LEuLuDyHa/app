package com.github.leuludyha.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Result of a call to the SearchApi.
 */
data class SearchResult(
    @SerializedName("numFound")
    val resultsCount: Int,
    @SerializedName("docs")
    val documents: List<Document>
): Serializable