package com.github.leuludyha.ibdb.webapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.leuludyha.ibdb.boredapi.ActivityResponse
import com.github.leuludyha.ibdb.boredapi.BoredActivity
import com.github.leuludyha.ibdb.boredapi.BoredApi
import com.github.leuludyha.ibdb.database.ActivityItem
import com.github.leuludyha.ibdb.database.ActivityRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebApiActivityViewModel(private val repository: ActivityRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<ActivityItem>> = repository.allActivities

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: ActivityItem) = viewModelScope.launch {
        repository.insert(word)
    }

    fun getRandomCachedActivity(onActivityReceived: (ActivityItem) -> Unit) = viewModelScope.launch {
        onActivityReceived(repository.randomActivity()?: ActivityItem("Something bad happened :("))
    }

    suspend fun deleteAllActivities() {
        repository.deleteAll()
    }

    fun requestActivity(boredApi: BoredApi, onActivityReceived: (ActivityResponse) -> Unit) {
//        // Create BoredAPI using Retrofit
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://www.boredapi.com/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val boredApi = retrofit.create(BoredApi::class.java)

        boredApi.getActivity().enqueue(object : Callback<BoredActivity> {
            val errorMessage = "Something bad happened :("
            override fun onResponse(call: Call<BoredActivity>, response: Response<BoredActivity>) {
                val body = response.body()?.activity ?: errorMessage

                // Signal the activity has been received
                onActivityReceived(ActivityResponse.Success(body))
                // Insert it in the database
                viewModelScope.launch {  insert(ActivityItem(body)) }
            }

            override fun onFailure(call: Call<BoredActivity>, t: Throwable) {
                onActivityReceived(ActivityResponse.Failure)
            }
        })
    }
}
