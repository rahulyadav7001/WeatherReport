package com.example.weatherreport.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class WeatherResponseObj(

  @SerializedName("cod") var cod: String? = null,
  @SerializedName("message") var message: Int? = null,
  @SerializedName("cnt") var cnt: Int? = null,
  @SerializedName("list") var weatherReportObjList: ArrayList<WeatherReportObj> = arrayListOf(),
  @SerializedName("city") var city: City? = City()

)