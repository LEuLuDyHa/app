package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RawSearch(
    @SerializedName("docs")
    val documents: List<RawDocument> = listOf(),
    @SerializedName("error")
    override val error: String?
): Serializable, ErrorProne, Raw<List<Work>> {
    override fun toModel(libraryApi: LibraryApi): List<Work> = documents
        .mapNotNull { it.toModel(libraryApi) }
}