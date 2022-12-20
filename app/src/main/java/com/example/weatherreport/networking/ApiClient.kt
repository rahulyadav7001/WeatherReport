package com.example.weatherreport.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    val mHttpLoginIntercepter = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val mClient = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS).addInterceptor(mHttpLoginIntercepter).build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(ServiceConstants.SERVICE_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}