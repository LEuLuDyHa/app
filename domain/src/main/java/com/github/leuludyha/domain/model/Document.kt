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

        builder.append("${title}, by ")

        if (authorNames != null) {
            for (name in authorNames) {
                builder.append("${name}, ")
            }
        } else {
            builder.append("an unknown author")
        }

        return builder.toString()
    }
}