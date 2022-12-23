package com.mynt.demo.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val retrofit = Retrofit.Builder()
    .baseUrl(GithubService.BASE_URL)
    .client(
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    )
    .addConverterFactory(GsonConverterFactory.create())
    .build()
