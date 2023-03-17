package com.github.leuludyha.data.api

interface Raw<T> {
    fun toModel(libraryApi: LibraryApi): T
}