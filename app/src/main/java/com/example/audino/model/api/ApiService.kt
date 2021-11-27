package com.example.audino.model.api

import com.example.audino.model.response.AllBooksResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("genre")
    suspend fun fetchAllBooks() : Response<AllBooksResponse>

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://audino.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getApiService() : ApiService = retrofit.create(ApiService::class.java)
    }
}