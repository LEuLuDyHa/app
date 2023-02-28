package com.github.leuludyha.ibdb.boredapi

sealed class ActivityResponse {
    data class Success(val content: String): ActivityResponse()
    object Failure: ActivityResponse()
}