package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User (
    @PrimaryKey
    val id : Int,
    @ColumnInfo
    val login : String,
    @ColumnInfo
    val password : String,
    @ColumnInfo
    val admin : Boolean
)