package com.example.commontask.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.commontask.MainWeatherAppActivity
import com.example.commontask.R
import com.example.commontask.controller.LocalForecastData
import com.example.commontask.model.ForecastDataModel
import com.example.commontask.model.TAG_A_MAIN
import com.example.commontask.model.TAG_O_BASE
import com.example.commontask.model.isErrorExecuted
import com.example.commontask.view.UserInterface


class MainActivity : BaseActivity() {
    private var storedForecastData: ForecastDataModel? = null
    private lateinit var userInterface: UserInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG_A_MAIN, "onCreate() is executed.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_weather)
        userInterface = UserInterface(this)
        storedForecastData = LocalForecastData(this).retrieve()
        if (storedForecastData != null) {
            Log.d(TAG_A_MAIN, "onResume() storedForecastData: $storedForecastData.")
            userInterface.updateUI(storedForecastData!!, false)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_A_MAIN, "onResume() is executed.")
        if (!isErrorExecuted) {
            userInterface.initialize()
        }
    }

    override fun onPause() {
        super.onPause()
        isErrorExecuted = false
    }

    override fun onBackPressed() {
        Log.d(TAG_O_BASE, "${javaClass.simpleName} onBackPressed")

       val  intent=Intent(this,MainWeatherAppActivity::class.java)
        startActivityForResult(intent,0)
    }

}

