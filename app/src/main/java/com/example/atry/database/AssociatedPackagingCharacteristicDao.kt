package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the AssociatedPackagingCharacteristic class with Room.

@Dao
interface AssociatedPackagingCharacteristicDao {

    @Insert
    fun insert(associatedPackagingCharacteristic: AssociatedPackagingCharacteristic)

    @Update
    fun update(associatedPackagingCharacteristic: AssociatedPackagingCharacteristic)

    @Query("SELECT * from associated_packaging_characteristic_table")
    fun getAll(): LiveData<List<AssociatedPackagingCharacteristic>>

    @Query("SELECT * from associated_packaging_characteristic_table WHERE idPackaging = :keyPackaging")
    fun get(keyPackaging: Int): LiveData<AssociatedPackagingCharacteristic>

    @Query("SELECT * from associated_packaging_characteristic_table WHERE idProduct = :key")
    fun getAllByProduct(key: Int): LiveData<List<AssociatedPackagingCharacteristic>>

    @Query("DELETE FROM associated_packaging_characteristic_table")
    fun clear()
}
