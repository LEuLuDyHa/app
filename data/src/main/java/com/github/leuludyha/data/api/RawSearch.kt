package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Raw response of the Search API. Not user friendly, needs to be converted to a `List<Document>`
 * before going to the `domain` layer.
 */
data class RawSearch(
    @SerializedName("docs")
    val documents: List<Document>?,
    @SerializedName("error")
    override val error: String?
): Serializable, ErrorProne, Raw<List<Work>> {
    override fun toModel(libraryApi: LibraryApi): List<Work> = documents
        ?.mapNotNull { it.toModel(libraryApi) }
        ?: listOf()
}