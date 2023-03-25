package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModel
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Work
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import java.io.Serializable

// TODO fetch editions somehow
data class RawWork(
    @SerializedName("key")
    val key: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("authors")
    val rawAuthors: List<RawWorkAuthor>?,
    @SerializedName("covers")
    val coverIds: List<Long>?,
    @SerializedName("subjects")
    val subjects: List<String>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("error")
    override val error: String?,
) : Serializable, ErrorProne, Raw<Work> {

    override fun toModel(libraryApi: LibraryApi): Work? {
        if (extractIdFromKey(key, "/works/") == null)
            return null

        val authors = flow {
            emit(rawAuthors
                .orEmpty()
                .mapNotNull { extractIdFromKey(it.rawKey?.key, "/authors/") }
                .map { libraryApi.getAuthor(it) }
                .mapNotNull { rawResponseToModel(it, libraryApi) }
            )
        }

        val editions = flow {
            emit(
                rawResponseToModel(
                    libraryApi
                        .getEditionsByWorkId(extractIdFromKey(key, "/works/")!!),
                    libraryApi
                )
            )
        }.mapNotNull { it?: listOf() }

        val covers = flow {
            emit(coverIds
                .orEmpty()
                .filter { it > 0 }
                .map { Cover(it) }
            )
        }

        return Work(
            id = extractIdFromKey(key, "/works/")!!,
            title = title,
            authors = authors,
            editions = editions,
            covers = covers,
            subjects = flow { emit(subjects.orEmpty()) }
        )
    }
    data class RawWorkAuthor(
        @SerializedName("author")
        val rawKey: RawKey?,
    )
}