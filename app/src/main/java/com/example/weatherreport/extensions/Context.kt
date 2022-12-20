package com.example.weatherreport.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable() : Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}