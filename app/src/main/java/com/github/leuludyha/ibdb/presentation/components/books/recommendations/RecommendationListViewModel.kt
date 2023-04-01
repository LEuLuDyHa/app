package com.github.leuludyha.ibdb.presentation.components.books.recommendations

import androidx.lifecycle.ViewModel
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.library.recommendation.RecommenderSystem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationListViewModel @Inject constructor(
    _recommender: RecommenderSystem,
    _authContext: AuthenticationContext,
) : ViewModel() {

    private val recommender = _recommender
    private val authContext = _authContext

    fun getRecommendations(): List<Work> {
        return recommender(authContext.principal)
    }
}