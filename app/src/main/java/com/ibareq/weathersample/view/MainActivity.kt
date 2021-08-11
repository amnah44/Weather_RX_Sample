package com.ibareq.weathersample.view

import android.view.LayoutInflater
import android.view.View
import com.ibareq.weathersample.databinding.ActivityMainBinding
import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.WeatherResponse
import com.ibareq.weathersample.presenter.MainPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.roundToInt

class MainActivity : BaseActivity<ActivityMainBinding>(), IMainView {

    private val disposable: CompositeDisposable = CompositeDisposable()
    //private val weatherRepository = WeatherRepository()
    private var presenter = MainPresenter(this)

    override val LOG_TAG: String = TAG

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setup() {

            binding!!.buttonSearch.setOnClickListener {
                presenter.getWeatherForCity(binding!!.inputCityName.text.toString())
            }
    }

    override fun addCallbacks() {}

    override fun onWeatherResult(response: Observable<Status<WeatherResponse>>) {
        disposable.add(
                 response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::getView)
        )

    }
    private fun getView(response: Status<WeatherResponse>){
        hideAllViews()
        when (response) {
            is Status.Error -> {
                binding!!.imageError.show()
            }
            is Status.Loading -> {
                binding!!.progressLoading.show()
            }
            is Status.Success -> {
                bindData(response.data)
            }
        }
    }

    override fun bindData(data: WeatherResponse) {
        binding!!.textMaxTemp.run {
            show()
            val maxTemp = data.consolidatedWeather[0].maxTemp.roundToInt().toString()
            val cityName = data.title
            text = "$cityName: $maxTemp"
        }
    }

    override fun hideAllViews() {
        binding!!.run {
            progressLoading.hide()
            textMaxTemp.hide()
            imageError.hide()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    companion object {
        const val TAG = "BK_PROGRAMMER"
    }

}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}



