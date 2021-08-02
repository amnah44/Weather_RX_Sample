package com.ibareq.weathersample.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.ibareq.weathersample.R
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import com.ibareq.weathersample.data.repository.*
import com.ibareq.weathersample.data.response.ConsolidatedWeather
import com.ibareq.weathersample.ui.adapter.WeatherAdapter
import com.ibareq.weathersample.util.Parser
import java.io.IOException
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var list: List<WeatherResponse>
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.buttonSearch.setOnClickListener {
//            getWeatherForCity(binding.inputCityName.text.toString())
//        }

        getWeatherForCity("london")
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun getWeatherForCity(cityName:String){
        disposable.add(
            WeatherRepository.getWeatherForCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onWeatherResult)
        )
    }

    private fun onWeatherResult(response: Status<WeatherResponse>){
        hideAllViews()
        when(response){
            is Status.Error -> {
                binding.imageError.show()
                binding.imageError2.show()

            }
            is Status.Loading -> {
               // binding.progressLoading.show()
//                binding.progress1.show()
                binding.loadding.show()
                binding.parentContainer.hide()

            }
            is Status.Success -> {
                bindData(response.data)

                binding.recyclerView.apply {
                    adapter = WeatherAdapter(response.data,this@MainActivity)
                    setHasFixedSize(true)

                }

            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun bindData(data: WeatherResponse){

        binding.textMaxTemp.run {
            show()
            val maxTemp = data.consolidatedWeather[0].maxTemp.roundToInt().toString()
            text = "$maxTemp ْْ℃"
        }
        val minTempDegree = data.consolidatedWeather[0].minTemp.roundToInt().toString()
        binding.apply {
            minTemp.text = "Min temp $minTempDegree ℃"
            cityName.text = data.title
            time.text = Parser.formatTime(Calendar.getInstance().time)
        }


        /*
        * get some data from api like
        * daily humidity
        * daily air pressure
        * daily wind speed
        */
        getWeatherState(data)

        //show icon of weather
        val icon = data.consolidatedWeather[0].weatherStateName
        binding.weatherState.text = icon
        getIconWeather(icon)

    }
    @SuppressLint("SetTextI18n")
    private fun getWeatherState(data:WeatherResponse){

        val humidity = data.consolidatedWeather[0].humidity.toString()
        val airPressure = data.consolidatedWeather[0].airPressure.roundToInt().toString()
        val windSpeed = data.consolidatedWeather[0].windSpeed.roundToInt().toString()

        binding.apply {
            stateHumidity.text = "$humidity%"
            stateAirPressure.text = "$airPressure kPa"
            stateWindSpeed.text = "$windSpeed m/s"
        }

    }

    private fun getIconWeather(icon:String){
        var url = ""
        when (icon){
            "Clear" -> {
                url = "https://image.flaticon.com/icons/png/128/4336/4336193.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)            }

            "Heavy Cloud" -> {
                url = "https://image.flaticon.com/icons/png/128/1146/1146869.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)
            }

            "Light Cloud" -> {
                url = "https://image.flaticon.com/icons/png/512/414/414825.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)            }

            "Light Rain" -> {
                url = "https://image.flaticon.com/icons/png/128/899/899693.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)            }

            "Heavy Rain" -> {
                url = "https://image.flaticon.com/icons/png/128/3937/3937493.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)            }

            "Showers" -> {
                url = "https://image.flaticon.com/icons/png/128/1327/1327601.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)            }

            "Dust" -> {
                url = "https://image.flaticon.com/icons/png/128/4336/4336193.png"
                Glide.with(this@MainActivity).load(url).into(binding.weatherIcon)            }
            else -> IOException()

        }

    }

    private fun hideAllViews(){
        binding.run {
           // progressLoading.hide()
            loadding.hide()
            imageError.hide()
            parentContainer.show()

        }
    }

    companion object{
        const val TAG = "BK_PROGRAMMER"
    }


}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}