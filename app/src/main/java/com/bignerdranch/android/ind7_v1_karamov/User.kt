package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User (
    @PrimaryKey
    val id : Int? = null,
    @ColumnInfo
    var login : String,
    @ColumnInfo
    var password : String,
    @ColumnInfo
    var admin : Boolean
)