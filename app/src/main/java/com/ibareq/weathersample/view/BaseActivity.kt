package com.ibareq.weathersample.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(){
    //initial variable for log fun
    abstract val LOG_TAG: String

    //initial binding inflater
    abstract val bindingInflater: (LayoutInflater) -> VB
    private var _binding: ViewBinding? = null
    protected val binding
        get() = _binding as VB?

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_Cotton)
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setup()
        addCallbacks()
    }

    //basic function in this project
    protected abstract fun setup()
    protected abstract fun addCallbacks()
    protected fun log(value: Any) {
        Log.v(LOG_TAG, value.toString())
    }
}