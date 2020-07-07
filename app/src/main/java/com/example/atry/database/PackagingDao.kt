package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the Packaging class with Room.

@Dao
interface PackagingDao {

    @Insert
    fun insert(packaging: Packaging)

    @Update
    fun update(packaging: Packaging)

    @Query("SELECT * from packaging_table")
    fun getAll(): LiveData<List<Packaging>>

    @Query("SELECT * from packaging_table WHERE id = :key")
    fun get(key: Int): LiveData<Packaging>

    @Query("DELETE FROM packaging_table")
    fun clear()
}