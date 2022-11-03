package com.example.uploadimageregister.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val BASE_URL = "https://market-final-project.herokuapp.com/"

    val instance : API by lazy {
        val service = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        service.create(API::class.java)
    }
}