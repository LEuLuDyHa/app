package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.library.Edition
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Editions of a given work.
 */
data class RawWorkEditions(
    @SerializedName("links")
    val links: RawWorkLinks?,
    @SerializedName("size")
    val editionsCount: Int?,
    @SerializedName("entries")
    val editions: List<RawEdition>?,
    @SerializedName("error")
    override val error: String?,
) : Serializable, ErrorProne, Raw<List<Edition>> {
    override fun toModel(libraryApi: LibraryApi): List<Edition> =
        editions?.map {rawEdition -> rawEdition.toModel(libraryApi) } ?: listOf()

}
