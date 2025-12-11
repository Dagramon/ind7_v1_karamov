package com.bignerdranch.android.ind7_v1_karamov

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

// TODO: FIX THIS SHIT AND MAKE DISCOUNTS

var boughtTours = mutableListOf<BoughtTour>(
    BoughtTour("Explore the heart of russia", "russia", "", ""),
    BoughtTour("America tour", "usa", "", ""),
    BoughtTour("Around turkey", "turkey", "", ""),
    BoughtTour("Brazil tour", "brazil", "", ""),
    BoughtTour("China tour", "china", "", ""),
)

class ToursFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tours, container, false)

        viewPager = view.findViewById(R.id.viewPager)

        viewPager.adapter = CustomPagerAdapter(boughtTours)

        viewPager.setPageTransformer { page, position ->
            val scale = 0.85f + (1 - Math.abs(position)) * 0.15f
            page.scaleX = scale
            page.scaleY = scale
        }

        for (i in 0 until boughtTours.size) {
            requestFlagAndPicture(boughtTours[i].country, i)
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
            boughtTours[index].flagUrl = countryObject.getJSONObject("flags").getString("png")
            Log.d("myLog", boughtTours[index].flagUrl)
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
                    boughtTours[index].imageUrl = mainObject.getJSONArray("results").getJSONObject(0).getJSONObject("urls").getString("full")
                    Log.d("myLog", boughtTours[index].imageUrl)
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