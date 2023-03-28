package com.github.leuludyha.data.db

import com.github.leuludyha.data.api.Raw

/**
 * Common interface for raw database types (used internally, that needs to be
 * converted before being passed to the model)
 */
interface Raw<T> {
    /**
     * Convert this [Raw] element to the corresponding model element
     */
    fun toModel(libraryDao: LibraryDao): T
}