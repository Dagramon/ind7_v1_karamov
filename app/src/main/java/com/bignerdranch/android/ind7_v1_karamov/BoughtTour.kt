package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BoughtTours")
data class BoughtTour
    (
    @PrimaryKey
    val id : Int,
    @ColumnInfo
    val userId : Int,
    @ColumnInfo
    val name : String,
    @ColumnInfo
    val country : String,
    @ColumnInfo
    val date : String,
    @ColumnInfo
    var flagUrl : String,
    @ColumnInfo
    var imageUrl: String
)