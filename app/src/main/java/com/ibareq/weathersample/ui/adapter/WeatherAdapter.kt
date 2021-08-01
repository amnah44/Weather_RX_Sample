package com.ibareq.weathersample.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibareq.weathersample.R
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.databinding.RawItemBinding
import com.ibareq.weathersample.util.Parser
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherAdapter(val weatherResponse
: WeatherResponse, val context: Context) :
    RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    inner class WeatherHolder(val binding: RawItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val view: RawItemBinding =
            RawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val current = weatherResponse.consolidatedWeather[position]
        val icon = current.weatherStateName
        holder.binding.apply {
            //change background of current item
            if (current.applicableDate == weatherResponse.consolidatedWeather[0].applicableDate) {
               changeCurrentItem(holder)
            }
            //get icon of state weather
            getIconWeather(icon,holder)

            //format date
            date.text = current.applicableDate

            //get max temperatures of next days
            val maxTemp = current.maxTemp.roundToInt().toString()
            temp.text = "$maxTemp ℃"
        }
    }
    private fun changeCurrentItem(holder: WeatherHolder){
        holder.binding.apply {
            weatherCard.background = ContextCompat.getDrawable(context,
                R.drawable.weather_current_item_shape
            )
            weatherCard.elevation = 24F
            date.setTextColor(Color.WHITE)
            temp.setTextColor(Color.WHITE)
        }

    }

    private fun getIconWeather(icon:String,holder: WeatherHolder){
        var url = ""
        when (icon){
            "Clear" -> {
                url = "https://image.flaticon.com/icons/png/128/4336/4336193.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)            }

            "Heavy Cloud" -> {
                url = "https://image.flaticon.com/icons/png/128/1146/1146869.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)
            }

            "Light Cloud" -> {
                url = "https://image.flaticon.com/icons/png/128/892/892313.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)             }

            "Light Rain" -> {
                url = "https://image.flaticon.com/icons/png/128/899/899693.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)             }

            "Heavy Rain" -> {
                url = "https://image.flaticon.com/icons/png/128/3937/3937493.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)            }

            "Showers" -> {
                url = "https://image.flaticon.com/icons/png/128/1327/1327601.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)              }

            "Dust" -> {
                url = "https://image.flaticon.com/icons/png/128/4336/4336193.png"
                Glide.with(context).load(url).into(holder.binding.imageIcon)
            }
            else -> IOException()

        }

    }

    override fun getItemCount() = weatherResponse.consolidatedWeather.count()


}