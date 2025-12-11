package com.bignerdranch.android.ind7_v1_karamov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

// TODO: ADD MORE SHIT HERE
class CustomPagerAdapter(private val items: MutableList<BoughtTour>) :
    RecyclerView.Adapter<CustomPagerAdapter.CustomViewHolder>() {

    fun updateData(newBoughtTours: MutableList<BoughtTour>) {
        boughtTours = newBoughtTours as MutableList<BoughtTour>
        notifyDataSetChanged()
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val flagView: ImageView = itemView.findViewById(R.id.flagView)
        val titleView: TextView = itemView.findViewById(R.id.titleText)
        val countryView: TextView = itemView.findViewById(R.id.countryText)
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
        holder.titleView.text = item.name
        holder.countryView.text = item.country
    }

    override fun getItemCount(): Int = items.size
}