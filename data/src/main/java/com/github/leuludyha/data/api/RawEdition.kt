package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModel
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Edition
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow
import java.io.Serializable

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
    override fun toModel(libraryApi: LibraryApi): Edition? {
        if (extractIdFromKey(key, "/books/") == null)
            return null

        val authors = flow {
            emit(authorRawKeys
                .orEmpty()
                .mapNotNull { extractIdFromKey(it.key, "/authors/") }
                .map { libraryApi.getAuthor(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
                .distinct()
            )
        }

        val works = flow {
            emit(workRawKeys
                .orEmpty()
                .mapNotNull { extractIdFromKey(it.key, "/works/") }
                .map { libraryApi.getWork(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
                .distinct()
            )
        }

        val covers = flow {
            emit(coverIds
                .orEmpty()
                .filter{ it > 0 }
                .map { Cover(it) }
                .distinct()
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
