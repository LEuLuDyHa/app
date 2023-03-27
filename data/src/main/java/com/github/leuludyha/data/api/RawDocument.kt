package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModel
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow

// TODO we can potentially fetch the subjects but it is not trivial

/**
 * Raw document among those returned by a search call to the [LibraryApi].
 */
data class RawDocument(
    @SerializedName("key")
    val key: String,
    @SerializedName("cover_i")
    val coverId: Long?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("author_name")
    val authorNames: List<String>?,
    @SerializedName("first_publish_year")
    val firstPublishYear: Int?,
    @SerializedName("author_key")
    val authorIds: List<String>?,
    @SerializedName("edition_key")
    val editionIds: List<String>?,
): Raw<Work> {
    override fun toModel(libraryApi: LibraryApi): Work? {
        if (extractIdFromKey(key, "/works/") == null)
            return null

        val editions = flow {
            emit(editionIds
                .orEmpty()
                .map { libraryApi.getEdition(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
                .distinct()
            )
        }

        val authors = flow {
            emit(authorIds
                .orEmpty()
                .map { libraryApi.getAuthor(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
                .distinct()
            )
        }

        val covers = flow {
            emit(if (coverId == null) listOf() else listOf(Cover(coverId)))
        }

        return Work(
            id = extractIdFromKey(key, "/works/")!!,
            title = title,
            authors = authors,
            editions = editions,
            covers = covers,
            subjects = flow { emit(listOf()) }
        )
    }
}