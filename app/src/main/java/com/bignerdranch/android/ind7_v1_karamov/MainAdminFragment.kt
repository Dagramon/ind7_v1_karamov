package com.bignerdranch.android.ind7_v1_karamov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainAdminFragment : Fragment() {

    var usersList = mutableListOf<User>()
    var currentToursList = mutableListOf<CurrentTour>()

    private lateinit var datePicker : DatePicker
    private lateinit var addTitleText : EditText
    private lateinit var addTourPrice : EditText
    private lateinit var countrySpinner : Spinner
    private lateinit var addTourButton : Button
    private lateinit var recyclerTours : RecyclerView
    private lateinit var recyclerUsers : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_admin, container, false)

        datePicker = view.findViewById(R.id.addTourDatePicker)
        addTitleText = view.findViewById(R.id.addTourTitle)
        addTourPrice = view.findViewById(R.id.addTourPrice)
        countrySpinner = view.findViewById(R.id.addTourSpinner)
        addTourButton = view.findViewById(R.id.addTourButton)

        addTourButton.setOnClickListener {

            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            val date = Calendar.getInstance().apply {
                set(
                    datePicker.year,
                    datePicker.month,
                    datePicker.dayOfMonth
                )
            }

            if (addTitleText.text.toString().isNotEmpty() && addTourPrice.text.toString().isNotEmpty())
            {
                var newTour = CurrentTour(null, addTitleText.text.toString(), countrySpinner.selectedItem.toString(), dateFormat.format(date.time), addTourPrice.text.toString().toInt(), "", "")
                val db = MainDB.getDb(this.requireContext())
                Thread{
                    if (!db.getTourDao().tourExists(newTour.name))
                    {
                        db.getTourDao().insertItem(newTour)
                    }
                }.start()

            }
        }

        datePicker.minDate = Calendar.getInstance().timeInMillis
        datePicker.maxDate = Calendar.getInstance().apply {
            add(Calendar.YEAR, 1)
        }.timeInMillis

        val db = MainDB.getDb(this.requireContext())

        db.getTourDao().getAllItems().asLiveData().observe(this.requireContext() as LifecycleOwner) {

            currentToursList.clear()
            it.forEach {
                currentToursList.add(
                    CurrentTour(
                        it.id,
                        name = it.name,
                        country = it.country,
                        date = it.date,
                        price = it.price,
                        imageUrl = "",
                        flagUrl = ""
                    )
                )
            }

            recyclerTours.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerTours.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int,
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_admin_tour, parent, false)
                    return object : RecyclerView.ViewHolder(view) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                    val titleText = holder.itemView.findViewById<TextView>(R.id.titleText)
                    val countryText = holder.itemView.findViewById<TextView>(R.id.countryText)
                    val dateText = holder.itemView.findViewById<TextView>(R.id.dateText)
                    val priceText = holder.itemView.findViewById<TextView>(R.id.priceText)
                    val buttonEdit = holder.itemView.findViewById<Button>(R.id.edittourButton)
                    val buttonRemove = holder.itemView.findViewById<Button>(R.id.removetourButton)

                    titleText.setText("TITLE: ${currentToursList[position].name}")
                    countryText.setText("COUNTRY: ${currentToursList[position].country}")
                    dateText.setText("DATE: ${currentToursList[position].date}")
                    priceText.setText("PRICE: ${currentToursList[position].price}$")

                    buttonEdit.setOnClickListener {

                        val editTourFragment = EditTourFragment(currentToursList[position])

                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, editTourFragment)
                            .addToBackStack("")
                            .commit()
                    }

                    buttonRemove.setOnClickListener {
                        Thread{
                            db.getTourDao().delete(currentToursList[position])
                        }.start()
                    }

                }

                override fun getItemCount() = currentToursList.size

            }
        }

        db.getUserDao().getAllItems().asLiveData().observe(this.requireContext() as LifecycleOwner) {

            usersList.clear()
            it.forEach {
                usersList.add(User(it.id, login = it.login, password = it.password, admin = it.admin))
            }

            recyclerTours.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerTours.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int,
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_admin_user, parent, false)
                    return object : RecyclerView.ViewHolder(view) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                    val loginText = holder.itemView.findViewById<TextView>(R.id.userLoginText)
                    val passwordText = holder.itemView.findViewById<TextView>(R.id.userPasswordText)
                    val adminCheckbox = holder.itemView.findViewById<CheckBox>(R.id.userAdminCheckbox)

                    val buttonRemove = holder.itemView.findViewById<Button>(R.id.removeUserButton)
                    val buttonEdit = holder.itemView.findViewById<Button>(R.id.editUserButton)

                    buttonRemove.setOnClickListener {
                        Thread{
                            db.getUserDao().delete(usersList[position])
                        }.start()
                    }

                    buttonEdit.setOnClickListener {

                        val editUserFragment = EditUserFragment(usersList[position])

                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, editUserFragment)
                            .addToBackStack("")
                            .commit()

                    }

                    loginText.setText(usersList[position].login)
                    passwordText.setText(usersList[position].password)
                    adminCheckbox.isChecked = usersList[position].admin

                }

                override fun getItemCount() = usersList.size

            }
        }

        return view
    }

}