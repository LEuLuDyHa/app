package com.github.leuludyha.data.api

import com.google.gson.annotations.SerializedName

/**
 * Used in some API responses. Basically a key with one supplementary level of indirection
 * (`"authors": ["key": "/authors/...", ..., "key": "/authors/..."]`)
 */
data class RawKey(
    @SerializedName("key")
    val key: String?
)
