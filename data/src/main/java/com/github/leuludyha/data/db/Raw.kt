package com.github.leuludyha.data.db

/**
 * Common interface for raw database types (used internally, that needs to be
 * converted before being passed to the model)
 */
interface Raw<T> {
    fun toModel(libraryDao: LibraryDao): T
}