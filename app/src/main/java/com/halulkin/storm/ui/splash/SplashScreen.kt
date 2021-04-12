package com.halulkin.storm.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.storm.R
import com.halulkin.storm.MainActivity

class SplashScreen : AppCompatActivity() {
    private var splashTimer: CountDownTimer? = null
    private val minute = 2L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        splashTimer = object : CountDownTimer(minute * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val mainIntent = Intent(applicationContext, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }.start()
    }

    override fun onStop() {
        super.onStop()
        cancelTimer()
    }

    private fun cancelTimer() {
        if (splashTimer != null)
            splashTimer!!.cancel()
    }
}