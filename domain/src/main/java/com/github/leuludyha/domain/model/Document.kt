package com.github.leuludyha.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Document returned by the SearchApi
 * @param key document's key to fetch it in other APIs
 */
data class Document(
    @SerializedName("cover_i")
    val coverId: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("author_name")
    val authorNames: List<String>?,
    @SerializedName("first_publish_year")
    val firstPublishYear: Int?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("author_key")
    val authorKeys: List<String>?
) {
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