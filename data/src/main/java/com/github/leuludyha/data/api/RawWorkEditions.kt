package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Edition
import com.google.gson.annotations.SerializedName

data class RawWorkEditions(
    @SerializedName("links")
    val links: RawWorkLinks?,
    @SerializedName("size")
    val editionsCount: Int?,
    @SerializedName("entries")
    val editions: List<RawEdition>?,
    @SerializedName("error")
    override val error: String?,
): ErrorProne, Raw<List<Edition>> {
    override fun toModel(libraryApi: LibraryApi): List<Edition> =
        editions?.mapNotNull {rawEdition -> rawEdition.toModel(libraryApi) } ?: listOf()


}
