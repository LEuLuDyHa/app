package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.CoverSize
import com.github.leuludyha.domain.model.Result
import retrofit2.Response

object ApiHelper {
    fun coverIdsToCoverUrls(coverIds: List<Long>?): List<(CoverSize) -> String>? =
        coverIds?.map(::coverIdToCoverUrl)?.ifEmpty { null }
    private fun coverIdToCoverUrl(coverId: Long) = { coverSize: CoverSize ->
        "https://covers.openlibrary.org/b/id/${coverId}-${coverSize}.jpg"
    }

    fun <TRaw, TModel> rawResponseToModelResult(response: Response<TRaw>, libraryApi: LibraryApi): Result<TModel>
            where TRaw : Raw<TModel>, TRaw: ErrorProne
    {
        if(response.isSuccessful){
            // If an error occured => Result.Error
            response.body()?.error?.let { return Result.Error(response.body()!!.error!!) }

            response.body()?.let {result->
                return Result.Success(result.toModel(libraryApi))
            }
        }
        return Result.Error(response.message())
    }

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
        ?.mapNotNull { extractIdFrom(it, "/authors/") }
        ?.map { libraryApi.authorById(it) }
        ?.ifEmpty { null }
        ?.mapNotNull { rawResponseToModel(it, libraryApi) }

    suspend fun workKeysToWorks(workKeys: List<String>?, libraryApi: LibraryApi) = workKeys
        ?.mapNotNull { extractIdFrom(it, "/works/") }
        ?.map { libraryApi.workById(it) }
        ?.ifEmpty { null }
        ?.mapNotNull { rawResponseToModel(it, libraryApi) }

    fun extractIdFrom(key: String?, delimiter: String) =
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