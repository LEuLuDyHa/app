package com.github.leuludyha.data.api

/**
 * Common interface for [LibraryApi] responses type that can be an error.
 */
internal interface ErrorProne {
    val error: String?
}