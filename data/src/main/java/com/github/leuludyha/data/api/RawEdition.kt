package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFrom
import com.github.leuludyha.data.api.ApiHelper.toIds
import com.github.leuludyha.domain.model.Edition
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Raw response of the Editions API. Not user friendly. Used only in the `data` layer,
 * to be transformed to `Edition` before going into the `domain`.
 */
data class RawEdition(
    @SerializedName("title")
    val title: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("isbn_10")
    val isbn10: List<String>?,
    @SerializedName("isbn_13")
    val isbn13: List<String>?,
    @SerializedName("authors")
    val authorRawKeys: List<RawKey>?,
    @SerializedName("works")
    val workRawKeys: List<RawKey>?,
    @SerializedName("covers")
    val covers: List<Int>?,
    @SerializedName("error")
    override val error: String?,
): Serializable, ErrorProne, Raw<Edition> {

    override fun toModel(): Edition =
        Edition(
            title = this.title,
            id = extractIdFrom(this.key, "/books/"),
            authorIds =  this.authorRawKeys?.toIds("/authors/"),
            workIds = this.workRawKeys?.toIds("/works/"),
            coverIds = covers,
        )
}
