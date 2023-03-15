package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Unfortunately not possible to inherit a data class, so I couldn't define a superclass `RawLinked`

/**
 * Works of a given author
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
): Serializable, ErrorProne, Raw<List<Work>> {
    override fun toModel(): List<Work> =
        works?.map {rawWork -> rawWork.toModel() } ?: listOf()
}