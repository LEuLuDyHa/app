package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModel
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow

/**
 * Raw editions's response of the API. Not user friendly, used internally and then converted into
 * the [Edition] model class.
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
): ErrorProne, Raw<Edition> {
    override fun toModel(libraryApi: LibraryApi): Edition? {
        if (extractIdFromKey(key, "/books/") == null || error != null)
            return null

        val authors = flow {
            emit(authorRawKeys
                .orEmpty()
                .distinct()
                .mapNotNull { extractIdFromKey(it.key, "/authors/") }
                .map { libraryApi.getAuthor(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
            )
        }

        val works = flow {
            emit(workRawKeys
                .orEmpty()
                .distinct()
                .mapNotNull { extractIdFromKey(it.key, "/works/") }
                .map { libraryApi.getWork(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
            )
        }

        val covers = flow {
            emit(coverIds
                .orEmpty()
                .distinct()
                .filter{ it > 0 }
                .map { Cover(it) }
            )
        }

        return Edition(
            id = extractIdFromKey(key, "/books/")!!,
            title = title,
            isbn10 = isbn10?.firstOrNull(),
            isbn13 = isbn13?.firstOrNull(),
            authors = authors,
            works = works,
            covers = covers,
        )
    }
}
