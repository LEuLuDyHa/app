package com.github.leuludyha.ibdb.boredapi

import retrofit2.Call
import retrofit2.http.GET

interface BoredApi {
    // @GET means we do a HTTP GET request
    // "activity" means we're looking for the "activity" field in the JSON response
    // Call's used an HTTP request
    @GET("activity")
    fun getActivity(): Call<BoredActivity>
}