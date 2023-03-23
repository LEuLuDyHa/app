package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.authorKeysToAuthors
import com.github.leuludyha.data.api.ApiHelper.coverIdsToCoverUrls
import com.github.leuludyha.data.api.ApiHelper.extractIdFrom
import com.github.leuludyha.data.api.ApiHelper.workKeysToWorks
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
    val coverIds: List<Long>?,
    @SerializedName("error")
    override val error: String?,
): Serializable, ErrorProne, Raw<Edition> {

    override fun toModel(libraryApi: LibraryApi): Edition = TODO()
}
