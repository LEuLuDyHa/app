package com.github.leuludyha.data.api

/**
 * Common interface for Api's response that can be an error.
 */
interface ErrorProne {
    val error: String?
}