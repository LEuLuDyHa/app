package com.github.leuludyha.data.api

import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow
import java.io.Serializable

/*
 * TODO try to understand the bio case:
 *  OL23919A (J.K. Rowling) has the bio json like that: "bio": "..."
 *  OL2674415A (Sara Woods) has the bio json like that: "bio": {"type": "...", "value": "..."}
 *  This causes a crash because bio is expecting a string but get instead an object
*/
data class RawAuthor(
    @SerializedName("key")
    val key: String?,
    @SerializedName("wikipedia")
    val wikipedia: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("birth_date")
    val birthDate: String?,
    @SerializedName("death_date")
    val deathDate: String?,
    @SerializedName("photos")
    val photoIds: List<Long>?,
    //@SerializedName("bio")
    //val bio: String?,
    @SerializedName("error")
    override val error: String?
): Serializable, ErrorProne, Raw<Author> {

    override fun toModel(libraryApi: LibraryApi): Author? {
        if (extractIdFromKey(key, "/authors/") == null)
            return null

        val photos = flow {
            emit(photoIds
                .orEmpty()
                .filter { it > 0 }
                .map { Cover(it) }
            )
        }

        return Author(
            id = extractIdFromKey(key, "/authors/")!!,
            name = name,
            birthDate = birthDate,
            deathDate = deathDate,
            //bio = bio,
            wikipedia = wikipedia,
            photos = photos
        )
    }

}
