package com.github.leuludyha.data.db

interface Raw<T> {
    fun toModel(libraryDao: LibraryDao): T
}