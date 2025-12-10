package com.bignerdranch.android.ind7_v1_karamov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.ind7_v1_karamov.fragments.MainClientFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null)
        {
            val mainClientFragment = MainClientFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, mainClientFragment)
                .commit()
        }

    }
}