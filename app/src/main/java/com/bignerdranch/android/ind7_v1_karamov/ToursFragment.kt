package com.bignerdranch.android.ind7_v1_karamov

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

var currentTours = mutableListOf<CurrentTour>()

class ToursFragment(val user: User) : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonBack : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tours, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        buttonBack = view.findViewById(R.id.buttonBack)

        buttonBack.setOnClickListener {

            val mainClientFragment = MainClientFragment(user)

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, mainClientFragment)
                .commit()

        }

        val db = MainDB.getDb(this.requireContext())
        db.getTourDao().getAllItems().asLiveData().observe(this.requireContext() as LifecycleOwner) {

            currentTours.clear()

            it.forEach {
                currentTours.add(CurrentTour(it.id, name = it.name, country = it.country, date = it.date, price = it.price, flagUrl = it.flagUrl, imageUrl = it.imageUrl))
            }

            viewPager.adapter = CustomPagerAdapter(currentTours, context as Context, user, parentFragmentManager)

            viewPager.setPageTransformer { page, position ->
                val scale = 0.85f + (1 - Math.abs(position)) * 0.15f
                page.scaleX = scale
                page.scaleY = scale
            }

            for (i in 0 until currentTours.size) {
                requestFlagAndPicture(currentTours[i].country, i)
            }

        }


        return view
    }

    private fun requestFlagAndPicture(country: String, index: Int) {
        val url = "https://restcountries.com/v3.1/name/$country"

        val queue = Volley.newRequestQueue(requireContext())
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseTourData(result, index)
            },
            { error ->
                getContext()?.let {
                    Toast.makeText(it, error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        )
        queue.add(request)
    }

    private fun parseTourData(result: String, index: Int) {
        try {
            val mainObject = JSONArray(result)
            val countryObject: JSONObject = mainObject.getJSONObject(0)
            currentTours[index].flagUrl = countryObject.getJSONObject("flags").getString("png")
            Log.d("myLog", currentTours[index].flagUrl)
            requestCapitalPicture(countryObject.getJSONArray("capital").getString(0), index)
        } catch (e: Exception) {
            Log.e("MyAppTag", "Error parsing tour data", e)
        }
    }

    private fun requestCapitalPicture(capital: String, index: Int) {
        val key = "4o1ii7y8ZafX76F1gVBQD_YoTozmEqH1Am3XCNDUJ4c"
        val url = "https://api.unsplash.com/search/photos?query=${capital}&count=1&orientation=portrait&client_id=$key"

        val queue = Volley.newRequestQueue(requireContext())
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                try {
                    val mainObject = JSONObject(result)
                    currentTours[index].imageUrl = mainObject.getJSONArray("results").getJSONObject(0).getJSONObject("urls").getString("regular")
                    Log.d("myLog", currentTours[index].imageUrl)
                } catch (e: Exception) {
                }
            },
            { error ->
                Log.d("MyAppTag", "ссылка: ${error}")
                getContext()?.let {
                    Toast.makeText(it, error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        )
        queue.add(request)
    }
}