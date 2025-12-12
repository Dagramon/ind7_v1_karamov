package com.bignerdranch.android.ind7_v1_karamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var i :Intent = getIntent()

        val admin: Boolean = i.getBooleanExtra("USER_ADMIN", false)

        var user = i.getParcelableExtra<User>("USER")

        val db = MainDB.getDb(this)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null)
        {
            if (admin)
            {
                if (user != null)
                {
                    val mainAdminFragment = MainAdminFragment(user)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, mainAdminFragment)
                        .commit()
                }
            }
            else
            {
                if (user != null)
                {
                    val mainClientFragment = MainClientFragment(user)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, mainClientFragment)
                        .commit()
                }
            }
        }

    }
}