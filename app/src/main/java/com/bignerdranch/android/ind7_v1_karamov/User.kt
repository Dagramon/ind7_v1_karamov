package com.bignerdranch.android.ind7_v1_karamov

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Users")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @ColumnInfo(name = "login")
    var login : String,
    @ColumnInfo(name = "password")
    var password : String,
    @ColumnInfo(name = "admin")
    var admin : Boolean
) : Parcelable