package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Unfortunately not possible to inherit a data class, so I couldn't define a superclass `RawLinked`

// TODO it might not suit our needs like that, we have to invest using paging
data class RawAuthorWorks (
    @SerializedName("links")
    val links: RawAuthorLinks?,
    @SerializedName("size")
    val worksCount: Int?,
    @SerializedName("entries")
    val works: List<RawWork>?,
    @SerializedName("error")
    override val error: String?,
): Serializable, ErrorProne, Raw<List<Work>> {
    override fun toModel(libraryApi: LibraryApi): List<Work>? =
        if (error != null)
            null
        else
            works?.mapNotNull {rawWork -> rawWork.toModel(libraryApi) } ?: listOf()
}