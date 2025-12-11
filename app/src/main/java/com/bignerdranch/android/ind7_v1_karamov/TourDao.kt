package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TourDao {
    @Insert
    fun insertItem(item : CurrentTour)
    @Query("SELECT * FROM CurrentTours")
    fun getAllItems() : Flow<List<CurrentTour>>
    @Query("SELECT EXISTS(SELECT 1 FROM currenttours WHERE name = :sentname LIMIT 1)")
    fun tourExists(sentname : String): Boolean
    @Delete
    fun delete(item : CurrentTour)
    @Update
    fun updateTour(item : CurrentTour)
}