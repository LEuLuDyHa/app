package com.github.leuludyha.data.api

import com.google.gson.annotations.SerializedName

// Unfortunately not possible to inherit a data class, so I couldn't define a superclass `RawLinks`
// TODO it might not suit our needs like that, we have to invest using paging

/**
 * Raw author links' response of the API. Used internally in some api queries.
 */
data class RawAuthorLinks(
    @SerializedName("author")
    val authorKey: String?,
    @SerializedName("prev")
    val prevKey: String?,
    @SerializedName("next")
    val nextKey: String?,
)
