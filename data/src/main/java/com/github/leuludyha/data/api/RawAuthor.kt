package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow
import java.io.Serializable

data class RawAuthor(
    @SerializedName("key")
    val key: String?,
    @SerializedName("wikipedia")
    val wikipedia: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("birth_date")
    val birthDate: String?,
    @SerializedName("photos")
    val photoIds: List<Long>?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("entity_type")
    val entityType: String?,
    @SerializedName("error")
    override val error: String?
): Serializable, ErrorProne, Raw<Author> {

    override fun toModel(libraryApi: LibraryApi): Author? {
        if (extractIdFromKey(key, "/authors/") == null)
            return null

        val photos = flow {
            emit(photoIds
                .orEmpty()
                .map { Cover(it) }
            )
        }

        return Author(
            id = extractIdFromKey(key, "/authors/")!!,
            name = name,
            bio = bio,
            wikipedia = wikipedia,
            entityType = entityType,
            photos = photos
        )
    }

}
