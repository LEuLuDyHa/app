package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.authorKeysToAuthors
import com.github.leuludyha.data.api.ApiHelper.coverIdsToCoverUrls
import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import com.github.leuludyha.data.api.ApiHelper.extractIdFrom

/**
 * Raw response of the Work API. Not user friendly. Used only in the `data` layer,
 * to be transformed to `Work` before going into the `domain`.
 */
data class RawWork(
    @SerializedName("title")
    val title: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("authors")
    val rawAuthors: List<RawWorkAuthor>?,
    @SerializedName("covers")
    val coverIds: List<Long>?,
    @SerializedName("subjects")
    val subjects: List<String>?,
    @SerializedName("error")
    override val error: String?,
) : Serializable, ErrorProne, Raw<Work> {

    override fun toModel(libraryApi: LibraryApi): Work {
        val authorKeys = rawAuthors
            ?.mapNotNull { it.rawKey?.key }
        return Work(
            title = this.title,
            id = extractIdFrom(this.key, "/works/"),
            fetchAuthors = { authorKeysToAuthors(authorKeys, libraryApi) },
            coverUrls = coverIdsToCoverUrls(coverIds),
            subjects = this.subjects,
        )
    }

    data class RawWorkAuthor(
        @SerializedName("author")
        val rawKey: RawKey?,
    )
}