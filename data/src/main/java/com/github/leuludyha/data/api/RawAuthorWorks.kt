package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName

// Unfortunately not possible to inherit a data class, so I couldn't define a superclass `RawLinked`

// TODO it might not suit our needs like that, we have to invest using paging

/**
 * Raw author works' response of the API. Not user friendly, used internally and then converted into
 * the `List<`[Work]`>` model class.
 */
data class RawAuthorWorks (
    @SerializedName("links")
    val links: RawAuthorLinks?,
    @SerializedName("size")
    val worksCount: Int?,
    @SerializedName("entries")
    val works: List<RawWork>?,
    @SerializedName("error")
    override val error: String?,
): ErrorProne, Raw<List<Work>> {
    override fun toModel(libraryApi: LibraryApi): List<Work>? =
        if (error != null)
            null
        else
            works?.mapNotNull {rawWork -> rawWork.toModel(libraryApi) } ?: listOf()
}