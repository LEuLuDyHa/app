package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Result
import retrofit2.Response

/**
 * Provides helper methods that are repeatedly used by the api code.
 */
object ApiHelper {

    /**
     * Converts a raw response from the api to a model result.
     */
    fun <TRaw, TModel> rawResponseToModelResult(response: Response<TRaw>, libraryApi: LibraryApi): Result<TModel>
            where TRaw : Raw<TModel>, TRaw: ErrorProne
    {
        if(response.isSuccessful){
            // If an error occured => Result.Error
            response.body()?.error?.let { return Result.Error(response.body()!!.error!!) }

            response.body()?.let { result->
                result.toModel(libraryApi)?.let { return Result.Success(it) }
                return Result.Error(response.message())
            }
        }
        return Result.Error(response.message())
    }

    /**
     * Converts a raw response from the api to a model class.
     */
    fun <TRaw, TModel> rawResponseToModel(response: Response<TRaw>, libraryApi: LibraryApi): TModel?
            where TRaw : Raw<TModel>, TRaw: ErrorProne
    {
        if(response.isSuccessful){
            response.body()?.error?.let { return null }

            response.body()?.let {result->
                return result.toModel(libraryApi)
            }
        }
        return null
    }

    suspend fun authorKeysToAuthors(authorKeys: List<String>?, libraryApi: LibraryApi) = authorKeys
        ?.mapNotNull { extractIdFromKey(it, "/authors/") }
        ?.map { libraryApi.getAuthor(it) }
        ?.ifEmpty { null }
        ?.mapNotNull { rawResponseToModel(it, libraryApi) }

    suspend fun workKeysToWorks(workKeys: List<String>?, libraryApi: LibraryApi) = workKeys
        ?.mapNotNull { extractIdFromKey(it, "/works/") }
        ?.map { libraryApi.getWork(it) }
        ?.ifEmpty { null }
        ?.mapNotNull { rawResponseToModel(it, libraryApi) }

    /**
     * Extracts an _OpenLibrary_ id from an _OpenLibrary_ key.
     *
     * Example:
     * ```
     * extractIdFromKey("/authors/OL23919A", "/authors/") == "OL23919A"
     * ```
     *
     * @param key _OpenLibrary_ key
     * @param delimiter delimiter for the key (for example, for authors it is "/authors/")
     * @return null if the extraction failed (wrong delimiter for example)
     */
    fun extractIdFromKey(key: String?, delimiter: String) =
        if (key?.substringAfter(delimiter) == key)
            null
        else
            key?.substringAfter(delimiter)

    fun List<RawKey>.toIds(keyDelimiter: String) = this
        .filter { rawKey -> rawKey.key != null } // All non-null authors
        .map { rawKey -> rawKey.key!!} // Map to their raw key
        .filter { key -> key.substringAfter(keyDelimiter) != key } // If not correct delimiter in the raw key, not author key
        .map { key ->  key.substringAfter(keyDelimiter) } // Keep only after delimiter
        .ifEmpty { null } // If empty list -> null (clearer imo)
}