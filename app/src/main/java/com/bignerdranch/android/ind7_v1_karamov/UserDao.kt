package com.bignerdranch.android.ind7_v1_karamov

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insertItem(item : User)
    @Query("SELECT EXISTS(SELECT 1 FROM Users WHERE login = :sentname LIMIT 1)")
    fun UserExists(sentname : String): Boolean
    @Query("SELECT EXISTS(SELECT 1 FROM Users WHERE login = :sentname AND admin = 1 LIMIT 1)")
    fun UserIsAdmin(sentname : String): Boolean
    @Query("SELECT * FROM Users")
    fun getAllItems() : Flow<List<User>>
    @Delete
    fun delete(item : User)
    @Update
    fun updateUser(item : User)
}