package com.bignerdranch.android.ind7_v1_karamov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainClientFragment(val user: User) : Fragment() {

    var boughtTourList = mutableListOf<BoughtTour>()
    private lateinit var buttonOpenTours : Button
    private lateinit var boughtRecycler : RecyclerView
    private lateinit var welcomeText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_client, container, false)

        buttonOpenTours = view.findViewById(R.id.buttonOpenTours)
        welcomeText = view.findViewById(R.id.welcomeText)
        boughtRecycler = view.findViewById(R.id.boughtRecycler)

        welcomeText.setText("Hello, ${user.login}")

        val db = MainDB.getDb(this.requireContext())

        db.getBoughtTourDao().getAllUserItems(user.login).asLiveData().observe(this.requireContext() as LifecycleOwner) {

            boughtTourList.clear()
            it.forEach {
                boughtTourList.add(
                    BoughtTour(
                        it.id,
                        username = it.username,
                        userLogin = it.userLogin,
                        name = it.name,
                        country = it.country,
                        date = it.date,
                        imageUrl = "",
                        flagUrl = ""
                    )
                )
            }

            boughtRecycler.layoutManager = LinearLayoutManager(this.requireContext())
            boughtRecycler.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int,
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_user_tour, parent, false)
                    return object : RecyclerView.ViewHolder(view) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                    val titleText = holder.itemView.findViewById<TextView>(R.id.itemTitle)
                    val countryText = holder.itemView.findViewById<TextView>(R.id.itemCountry)
                    val dateText = holder.itemView.findViewById<TextView>(R.id.itemDate)
                    val usernameText = holder.itemView.findViewById<TextView>(R.id.itemUsername)

                    titleText.setText(boughtTourList[position].name)
                    countryText.setText(boughtTourList[position].country)
                    dateText.setText(boughtTourList[position].date)
                    usernameText.setText(boughtTourList[position].username)

                }

                override fun getItemCount() = boughtTourList.size

            }
        }

        buttonOpenTours.setOnClickListener {

            val toursFragment = ToursFragment(user)

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, toursFragment)
                .addToBackStack("")
                .commit()
        }

        return view
    }
}