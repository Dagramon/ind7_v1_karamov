package com.bignerdranch.android.ind7_v1_karamov

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CustomPagerAdapter(private val items: MutableList<CurrentTour>, private val context: Context,val user : User,val fragmentManager : FragmentManager) :
    RecyclerView.Adapter<CustomPagerAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val flagView: ImageView = itemView.findViewById(R.id.flagView)
        val titleView: TextView = itemView.findViewById(R.id.titleText)
        val priceView : TextView = itemView.findViewById(R.id.priceText)
        val userName : EditText = itemView.findViewById(R.id.buyerName)
        val countryView: TextView = itemView.findViewById(R.id.countryText)
        val dateView : TextView = itemView.findViewById(R.id.dateText)
        val buyButton : Button = itemView.findViewById(R.id.buyButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tour, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = items[position]
        if (item.imageUrl.isNotEmpty())
        {
            Picasso
                .get()
                .load(item.imageUrl)
                .placeholder(R.drawable.top_bar)
                .fit()
                .into(holder.imageView)
        }
        if (item.flagUrl.isNotEmpty())
        {
            Picasso
                .get()
                .load(item.flagUrl)
                .placeholder(R.drawable.top_bar)
                .fit()
                .into(holder.flagView)
        }
        holder.buyButton.setOnClickListener {

            if (holder.userName.text.toString().isNotEmpty())
            {
                val db = MainDB.getDb(context)
                Thread {

                    if (!db.getBoughtTourDao().tourBought(item.name, user.login, holder.userName.text.toString()))
                    {
                        var tour : BoughtTour = BoughtTour(null, user.login, item.name, holder.userName.text.toString(), item.country, item.date, item.flagUrl, item.imageUrl)
                        db.getBoughtTourDao().insertItem(tour)

                        val mainClientFragment = MainClientFragment(user)
                        fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, mainClientFragment)
                            .commit()
                    }

                }.start()
            }
            else
            {
                Snackbar.make(holder.userName, "Введите имя покупателя", Snackbar.LENGTH_SHORT).show()
            }

        }
        holder.dateView.setText(item.date)
        holder.priceView.setText("${item.price * 0.85}$ with 15%")
        holder.titleView.text = item.name
        holder.countryView.text = item.country
    }

    override fun getItemCount(): Int = items.size
}