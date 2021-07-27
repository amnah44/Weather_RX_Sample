package com.ibareq.weathersample.ui

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import com.ibareq.weathersample.data.repository.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            getWeatherForCity(binding.inputCityName.text.toString())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun getWeatherForCity(cityName: String){
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
                binding.searchLottie.hide()
                binding.question.hide()
                binding.description.hide()
                binding.textMaxTempDescription.hide()
                binding.retryMsg.show()
                binding.imageError.show()

            }
            is Status.Loading -> {
                binding.searchLottie.hide()
                binding.question.hide()
                binding.description.hide()
                binding.retryMsg.hide()
                binding.imageError.hide()
                binding.progressLoading.show()
            }
            is Status.Success -> {
                bindData(response.data)
            }
        }
    }

    private fun bindData(data: WeatherResponse){
        binding.searchLottie.hide()
        binding.question.hide()
        binding.description.hide()
        binding.retryMsg.hide()
        binding.textMaxTempDescription.show()
        binding.textMaxTemp.run {
            show()
            val maxTemp = data.consolidatedWeather[0].maxTemp.roundToInt().toString()
            val cityName = data.title
            text = "$cityName  $maxTemp ْْ°C"
        }
    }

    private fun hideAllViews(){
        binding.run {
            progressLoading.hide()
            textMaxTemp.hide()
            imageError.hide()

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