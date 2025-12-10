package com.bignerdranch.android.ind7_v1_karamov.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bignerdranch.android.ind7_v1_karamov.R
class MainClientFragment : Fragment() {

    private lateinit var buttonOpenTours : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_client, container, false)

        buttonOpenTours = view.findViewById(R.id.buttonOpenTours)

        buttonOpenTours.setOnClickListener {

            val toursFragment = ToursFragment()

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, toursFragment)
                .addToBackStack("")
                .commit()
        }

        return view
    }
}