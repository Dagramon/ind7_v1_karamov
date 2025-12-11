package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentTours")
data class CurrentTour
    (
    @PrimaryKey
    val id : Int? = null,
    @ColumnInfo
    var name : String,
    @ColumnInfo
    var country : String,
    @ColumnInfo
    var date : String,
    @ColumnInfo
    var price : Int,
    @Ignore
    var flagUrl : String,
    @Ignore
    var imageUrl: String
)