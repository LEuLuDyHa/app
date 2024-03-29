package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.library.Work
import com.google.gson.annotations.SerializedName

/**
 * Raw result of a search by the [LibraryApi], contains a list of [RawDocument]s.
 */
data class RawSearch(
    @SerializedName("docs")
    val documents: List<RawDocument> = listOf(),
    @SerializedName("error")
    override val error: String?
): ErrorProne, Raw<List<Work>> {
    override fun toModel(libraryApi: LibraryApi): List<Work> = documents
        .mapNotNull { it.toModel(libraryApi) }
}