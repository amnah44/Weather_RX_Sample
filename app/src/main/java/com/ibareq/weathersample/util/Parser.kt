package com.ibareq.weathersample.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Parser {

    companion object{
        fun formatTime(date:Date):String{
            val sampleDateFormat: SimpleDateFormat = SimpleDateFormat.getDateInstance() as SimpleDateFormat
            sampleDateFormat.applyPattern("HH:mm")
            return sampleDateFormat.format(date)
        }
        @SuppressLint("SimpleDateFormat")
        fun formatDate(date:String):String{
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd")
            return formatter.format(parser.parse(date))
        }
    }
}