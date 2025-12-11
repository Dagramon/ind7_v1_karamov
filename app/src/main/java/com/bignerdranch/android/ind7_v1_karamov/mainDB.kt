package com.bignerdranch.android.ind7_v1_karamov

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CurrentTour::class, BoughtTour::class, User::class], version = 1, exportSchema = false)
abstract class MainDB : RoomDatabase() {
    abstract fun getUserDao() : UserDao
    abstract fun getTourDao() : TourDao
    companion object{
        fun getDb(context: Context): MainDB
        {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "Main.db"
            ).build()
        }
    }
}