package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import com.github.leuludyha.data.api.ApiHelper.extractIdFrom
import com.github.leuludyha.data.api.ApiHelper.toIds

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
    val covers: List<Int>?,
    @SerializedName("subjects")
    val subjects: List<String>?,
    @SerializedName("error")
    override val error: String?,
) : Serializable, ErrorProne, Raw<Work> {

    override fun toModel(): Work = Work(
            title = this.title,
            id = extractIdFrom(this.key, "/works/"),
            authorIds = this.rawAuthors
                ?.filter { rawAuthor -> rawAuthor.rawKey != null }
                ?.map { rawAuthor -> rawAuthor.rawKey!! }
                ?.toIds("/authors/"),
            coverIds = this.covers,
            subjects = this.subjects,
        )

    data class RawWorkAuthor(
        @SerializedName("author")
        val rawKey: RawKey?,
    )
}