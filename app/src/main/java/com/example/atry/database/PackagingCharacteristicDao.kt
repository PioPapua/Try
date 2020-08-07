package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the PackagingCharacteristic class with Room.

@Dao
interface PackagingCharacteristicDao {

    @Insert
    fun insert(packagingCharacteristic: PackagingCharacteristic)

    @Update
    fun update(packagingCharacteristic: PackagingCharacteristic)

    @Query("SELECT * from packaging_characteristic_table")
    fun getAll(): List<PackagingCharacteristic>

    @Query("SELECT id from packaging_characteristic_table WHERE category = :keyCategory AND description = :keyDescription")
    fun getIdByCategoryAndDescription(keyCategory: String, keyDescription: String): Int

    @Query("SELECT * from packaging_characteristic_table WHERE id = :key")
    fun get(key: Int): PackagingCharacteristic?

    @Query("DELETE FROM packaging_characteristic_table")
    fun clear()
}
