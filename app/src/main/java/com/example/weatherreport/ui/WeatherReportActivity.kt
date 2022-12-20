package com.example.weatherreport.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.R
import com.example.weatherreport.databinding.ActivityWeatherReportBinding
import com.example.weatherreport.extensions.isNetworkAvailable
import com.example.weatherreport.model.WeatherReportObj
import com.example.weatherreport.model.WeatherResponseObj
import com.example.weatherreport.networking.ApiClient
import com.example.weatherreport.networking.ServiceConstants
import com.example.weatherreport.networking.WeatherAPIService
import com.example.weatherreport.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherReportActivity : AppCompatActivity() {
    private var mWeatherReportList: ArrayList<WeatherReportObj> = arrayListOf()
    private lateinit var mWeatherReportAdapter: WeatherReportAdapter
    private lateinit var rvWeatherReport: RecyclerView
    private var binding: ActivityWeatherReportBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherReportBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initViews()
        bindViews()
    }

    private fun bindViews() {
        rvWeatherReport.layoutManager = LinearLayoutManager(this)
        mWeatherReportAdapter = WeatherReportAdapter(mWeatherReportList, this)
        rvWeatherReport.adapter = mWeatherReportAdapter
        binding?.layoutContentWeather?.btnLoadReport?.setOnClickListener {
            if (this.isNetworkAvailable()) {
                showData(value = false)
                showHideProgressDialog(value = true)
                getWeatherReportInfo()
            } else {
                Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        setSupportActionBar(binding?.toolbar)
        binding?.toolbarLayout?.title = title
        rvWeatherReport = binding?.layoutContentWeather?.rvWeatherReport!!
    }

    private fun getWeatherReportInfo() {
        val forcastApi = ApiClient.getInstance().create(WeatherAPIService::class.java)
        val response = forcastApi.getFiveDaysWeatherInfo(
            longitude = ServiceConstants.STATIC_LONGITUDE,
            latitude = ServiceConstants.STATIC_LATITUDE,
            appId = ServiceConstants.APP_KEY
        )
        response.enqueue(object : Callback<WeatherResponseObj?> {
            override fun onResponse(
                call: Call<WeatherResponseObj?>?,
                response: Response<WeatherResponseObj?>?
            ) {
                showHideProgressDialog(false)
                showData(value = true)
                if (response?.body() != null) {
                    binding?.layoutContentWeather?.tvPlace?.text = getString(R.string.placeTitle, response?.body()?.city?.name)
                    mWeatherReportList = response?.body()?.weatherReportObjList!!
                    mWeatherReportAdapter.refreshData(mWeatherReportList)
                } else {
                    binding?.layoutContentWeather?.tvPlace?.text = getString(R.string.error_Message)
                }
            }

            override fun onFailure(
                call: Call<WeatherResponseObj?>?,
                t: Throwable?
            ) {
                showHideProgressDialog(false)
                Log.d(Constant.MY_LOG, "Error: ${t?.printStackTrace()}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showHideProgressDialog(value: Boolean) {
        if (value) binding?.layoutContentWeather?.pbData?.visibility =
            View.VISIBLE else binding?.layoutContentWeather?.pbData?.visibility = View.GONE
    }

    fun showData(value: Boolean) {
        if (value) {
            rvWeatherReport.visibility = View.VISIBLE
            binding?.layoutContentWeather?.tvPlace?.visibility = View.VISIBLE
        } else {
            rvWeatherReport.visibility = View.GONE
            binding?.layoutContentWeather?.tvPlace?.visibility = View.GONE
        }
    }
}