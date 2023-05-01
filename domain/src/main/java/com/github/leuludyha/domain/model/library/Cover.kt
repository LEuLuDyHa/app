package com.github.leuludyha.domain.model.library

data class Cover(
    val id: Long
) {
    fun urlForSize(coverSize: CoverSize) =
        "https://covers.openlibrary.org/b/id/${id}-${coverSize}.jpg"
    fun fileNameForSize(coverSize: CoverSize) =
        "cover-${id}-${coverSize}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cover) return false

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
