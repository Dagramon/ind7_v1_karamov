package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentTours")
data class CurrentTour
    (
    @PrimaryKey
    val id : Int,
    @ColumnInfo
    val name : String,
    @ColumnInfo
    val country : String,
    @ColumnInfo
    val date : String,
    @ColumnInfo
    val price : Int,
    @Ignore
    var flagUrl : String,
    @Ignore
    var imageUrl: String
)