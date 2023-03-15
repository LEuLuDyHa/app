package com.github.leuludyha.data.api

object ApiHelper {
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