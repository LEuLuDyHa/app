package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFrom
import com.github.leuludyha.domain.model.Author
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RawAuthor(
    @SerializedName("wikipedia")
    val wikipedia: String?,
    @SerializedName("personal_name")
    val name: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("photos")
    val photoIds: List<Int>?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("entity_type")
    val entityType: String?,
    @SerializedName("error")
    override val error: String?
): Serializable, ErrorProne, Raw<Author> {

    override fun toModel(libraryApi: LibraryApi): Author = Author(
        wikipedia = wikipedia,
        name = name,
        id = extractIdFrom(key, "/authors/"),
        photoIds = photoIds,
        bio = bio,
        entityType = entityType,
    )
}
