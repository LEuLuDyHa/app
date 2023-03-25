package com.github.leuludyha.data.api

/**
 * Common interface for raw API responses types (used internally, that needs to be
 * converted before being passed to the model)
 */
interface Raw<T> {
    fun toModel(libraryApi: LibraryApi): T?
}