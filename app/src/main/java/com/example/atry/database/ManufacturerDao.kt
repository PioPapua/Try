package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the Manufacturer class with Room.

@Dao
interface ManufacturerDao {
    @Insert
    fun insert(manufacturer: Manufacturer)

    @Update
    fun update(manufacturer: Manufacturer)

    @Query("SELECT * from manufacturer_table WHERE id = :key")
    fun get(key: Long): Manufacturer?

    @Query("SELECT * from manufacturer_table")
    fun getAll(): LiveData<List<Manufacturer>>

    @Query("SELECT * from manufacturer_table ORDER BY id DESC LIMIT 1")
    fun getLast(): LiveData<List<Manufacturer>>

    @Query("SELECT * from manufacturer_table WHERE name = :key")
    fun getIdByName(key: String): Manufacturer?

    @Query("DELETE FROM manufacturer_table")
    fun clear()
}