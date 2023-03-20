package com.github.leuludyha.ibdb.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Work
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkListViewModel @Inject constructor(

) : ViewModel() {

    fun getAuthorsOf(work: Work, callback: (List<Author>?) -> Unit) {
        viewModelScope.launch {
            val result = work.authors()

            result?.let { callback(it) }
        }
    }

    fun displayAuthors(authors: List<Author>): String {
        if (authors.size == 1) {
            return authors[0].name.orEmpty(); }
        if (authors.size == 2) {
            return authors[0].name.orEmpty() + " and " + authors[1].name.orEmpty(); }
        val builder = StringBuilder()

        for ((counter, author) in authors.withIndex()) {
            builder.append(author.name.orEmpty())
            if (counter == authors.size - 1) {
                builder.append(" and ")
            } else builder.append(", ")
        }

        return builder.toString()
    }

}