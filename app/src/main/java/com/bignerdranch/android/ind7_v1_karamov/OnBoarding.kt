package com.bignerdranch.android.ind7_v1_karamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class OnBoarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)

        val timer = object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val intent = Intent(this@OnBoarding, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        timer.start()

    }
}