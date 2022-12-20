package com.example.weatherreport.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.R
import com.example.weatherreport.model.WeatherReportObj
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherReportAdapter(
    weatherReportList: ArrayList<WeatherReportObj>,
    context: Context
) :
    RecyclerView.Adapter<WeatherReportAdapter.ViewHolderItem>() {
    private var mWeatherReportList = weatherReportList
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.weather_item_cell, parent, false)
        return ViewHolderItem(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {

        val weatherReportObj = mWeatherReportList[position]
        holder.tv_date.text = getFormattedDate(weatherReportObj.dtTxt, isDate = true)
        holder.tv_time.text = getFormattedDate(weatherReportObj.dtTxt, isDate = false)
        holder.tv_temperature.text =
            mContext.getString(R.string.temperature_str, getTemperature(weatherReportObj))
        holder.tv_humidity.text =
            mContext.getString(R.string.humidity, weatherReportObj.main?.humidity.toString()) + "%"
        holder.tv_weatherDescription.text =
            mContext.getString(R.string.weather_desc, weatherReportObj.weather[0]?.description)
        holder.tv_windSpeed.text =
            mContext.getString(R.string.weather_desc, weatherReportObj.wind?.speed.toString())
    }

    private fun getTemperature(weatherReportObj: WeatherReportObj): String? {
        return ((Math.round(weatherReportObj.main?.temp?.minus(273.15)!!) * 100) / 100).toString()
    }

    @SuppressLint("NewApi")
    private fun getFormattedDate(date: String?, isDate: Boolean): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val dateTime = LocalDateTime.parse(date, dateTimeFormatter)
        return if (isDate) mContext.getString(
            R.string.date_str,
            dateTime.toLocalDate()
        ) else dateTime.format(timeFormatter)
    }

    override fun getItemCount(): Int {
        return mWeatherReportList.size
    }

    fun refreshData(weatherReportList: ArrayList<WeatherReportObj>) {
        mWeatherReportList = weatherReportList
        notifyDataSetChanged()
    }

    inner class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_date: TextView = itemView.findViewById(R.id.tv_date)
        val tv_time: TextView = itemView.findViewById(R.id.tv_time)
        val tv_temperature: TextView = itemView.findViewById(R.id.tv_temperature)
        val tv_weatherDescription: TextView = itemView.findViewById(R.id.tv_weatherDescription)
        val tv_humidity: TextView = itemView.findViewById(R.id.tv_humidity)
        val tv_windSpeed: TextView = itemView.findViewById(R.id.tv_windSpeed)
    }
}
