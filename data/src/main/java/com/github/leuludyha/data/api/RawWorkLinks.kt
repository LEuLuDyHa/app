package com.github.leuludyha.data.api

import com.google.gson.annotations.SerializedName

// Unfortunately not possible to inherit a data class, so I couldn't define a superclass `RawLinks`
data class RawWorkLinks(
    @SerializedName("work")
    val workKey: String?,
    @SerializedName("prev")
    val prevKey: String?,
    @SerializedName("next")
    val nextKey: String?,
)
