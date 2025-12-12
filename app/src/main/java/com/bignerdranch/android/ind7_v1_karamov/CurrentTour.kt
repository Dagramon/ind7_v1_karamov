package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentTours")
data class CurrentTour
    (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "country")
    var country : String,
    @ColumnInfo(name = "date")
    var date : String,
    @ColumnInfo(name = "price")
    var price : Int,
    @ColumnInfo(name = "flagUrl")
    var flagUrl : String,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String
)