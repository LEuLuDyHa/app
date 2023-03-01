package com.github.leuludyha.ibdb.boredapi

import android.app.Application
import com.github.leuludyha.ibdb.webapi.WebApiApplication
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BoredApi {
    // @GET means we do a HTTP GET request
    // "activity" means we're looking for the "activity" field in the JSON response
    // Call's used an HTTP request
    @GET("activity")
    fun getActivity(): Call<BoredActivity>

    companion object {
        fun getInstance(application: Application) = Retrofit.Builder()
            .baseUrl((application as WebApiApplication).getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(BoredApi::class.java)
    }
}