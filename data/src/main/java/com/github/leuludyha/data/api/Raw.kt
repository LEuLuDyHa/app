package com.github.leuludyha.data.api

/**
 * Common interface for raw API responses types (used internally, that needs to be
 * converted before being passed to the model)
 */
internal interface Raw<T> {
    /**
     * Convert this [Raw] element to the corresponding model element
     */
    fun toModel(libraryApi: LibraryApi): T?
}