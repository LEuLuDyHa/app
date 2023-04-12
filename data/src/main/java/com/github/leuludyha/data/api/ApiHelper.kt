package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.library.Result
import retrofit2.Response

/**
 * Provides helper methods that are repeatedly used by the api code.
 */
internal object ApiHelper {

    /**
     * Converts a raw response from the api to a model result.
     */
    fun <TRaw, TModel> rawResponseToModelResult(response: Response<TRaw>, libraryApi: LibraryApi): Result<TModel>
            where TRaw : Raw<TModel>, TRaw: ErrorProne
    {
        val body = response.body()
        if(response.isSuccessful && body != null){
            // If an error occured => Result.Error
            body.error?.let { return Result.Error(response.body()!!.error!!) }

            val toModel = body.toModel(libraryApi)
            return if(toModel != null) {
                Result.Success(toModel)
            } else {
                Result.Error(response.message())
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
        val body = response.body()
        if(response.isSuccessful && body != null) {
            body.error?.let { return null }
            return body.toModel(libraryApi)
        }
        return null
    }

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
        when (key) {
            null -> null
            key.substringAfter(delimiter) -> null
            else ->
                key.substringAfter(delimiter).ifEmpty { null }
        }
}