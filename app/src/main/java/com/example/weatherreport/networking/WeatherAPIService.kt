package com.example.weatherreport.networking

import com.example.weatherreport.model.WeatherResponseObj
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPIService {
    @GET("/data/2.5/forecast")
    fun getFiveDaysWeatherInfo(@Query("lat") latitude : String, @Query("lon") longitude : String, @Query("appid") appId : String ) : Call<WeatherResponseObj>
}