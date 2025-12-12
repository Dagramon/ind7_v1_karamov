package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BoughtTours")
data class BoughtTour
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "userLogin")
    val userLogin: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "flagUrl")
    var flagUrl: String,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String
)