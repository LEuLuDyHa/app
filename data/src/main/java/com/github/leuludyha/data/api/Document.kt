package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.authorKeysToAuthors
import com.github.leuludyha.data.api.ApiHelper.coverIdsToCoverUrls
import com.github.leuludyha.data.api.ApiHelper.extractIdFrom
import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * TODO I assumed the type of a search result (Document) was ALWAYS a work, is it?
 * Document returned by the SearchApi
 * @param key document's key to fetch it in other APIs
 */
data class Document(
    @SerializedName("key")
    val key: String?,
    @SerializedName("cover_i")
    val coverId: Long?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("author_name")
    val authorNames: List<String>?,
    @SerializedName("first_publish_year")
    val firstPublishYear: Int?,
    @SerializedName("author_key")
    val authorKeys: List<String>?,
    @SerializedName("edition_key")
    val editionIds: List<String>?,
): Serializable, Raw<Work> {
    override fun toModel(libraryApi: LibraryApi) = Work (
        title = title,
        id = extractIdFrom(key, "/works/").orEmpty(), // TODO CORRECT
        fetchAuthors = { authorKeysToAuthors(authorKeys, libraryApi) },
        coverUrls = coverIdsToCoverUrls(if (coverId == null) null else listOf(coverId)),
        subjects = listOf()
    )

    override fun toString(): String {
        val builder = StringBuilder()

        builder.append("${title?: "Unknown title"}, by ")

        if (authorNames != null && authorNames.isNotEmpty()) {
            for (name in authorNames.dropLast(2)) {
                builder.append("${name}, ")
            }
            if (authorNames.size == 1)
                builder.append(authorNames.last())
            else {
                builder.append("${authorNames.dropLast(1).last()} and ${authorNames.last()}")
            }
        } else {
            builder.append("an unknown author")
        }

        return builder.toString()
    }
}