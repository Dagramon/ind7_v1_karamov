package com.bignerdranch.android.ind7_v1_karamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    private lateinit var buttonEnter : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonEnter = findViewById(R.id.buttonEnter)

        buttonEnter.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}