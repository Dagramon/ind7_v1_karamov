package com.bignerdranch.android.ind7_v1_karamov.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel
import com.bignerdranch.android.ind7_v1_karamov.Classes.Tour
import com.bignerdranch.android.ind7_v1_karamov.R

class ToursFragment : Fragment() {

    val tours = arrayOf(Tour("tour1", "russia", R.drawable.icon))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tours, container, false)



        return view
    }

}