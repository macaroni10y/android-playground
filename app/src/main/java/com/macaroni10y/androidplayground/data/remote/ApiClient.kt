package com.macaroni10y.androidplayground.data.remote

import retrofit2.Retrofit
import retrofit2.create

object ApiClient {
    fun create(baseUrl: String): TodoApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
            .create(TodoApi::class.java)
    }
}
