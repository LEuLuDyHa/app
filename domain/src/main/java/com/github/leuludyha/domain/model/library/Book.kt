package com.github.leuludyha.domain.model.library

import java.util.*

data class Book(
    // Id from which we can find the rest of info on the book
    val id: String,
    // I think it is reasonable to always store the title, it might make it easier to debug also
    val title: String,
    // Also there is a subtitle
    val subtitle: String,
    // The isbn just to identify the book further if need be
    val isbn: String,
    // And the rest of book information which might or might not be present locally in the object,
    // in which case we have to fetch it in some database
    val info: Optional<BookInfo>,
)

// Use parameter jscmd=data to get these in API call
data class BookInfo(
    val nbOfPages: Int,
    val languages: List<String>,
    val authors: List<Author>,
    val publishers: List<String>,
    val subjects: List<String>,
    val notes: String,
    val coverUrls: BookCoverUrls,
    // Something called "By statement" in the API which might be useful to us
    val byStatement: String,
)

data class BookCoverUrls(
    val small: String,
    val medium: String,
    val large: String,
)