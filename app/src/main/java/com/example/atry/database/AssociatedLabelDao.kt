package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the AssociatedLabel class with Room.

@Dao
interface AssociatedLabelDao {

    @Insert
    fun insert(label: AssociatedLabel)

    @Update
    fun update(label: AssociatedLabel)

    @Query("SELECT * from associated_label_table")
    fun getAll(): LiveData<List<AssociatedLabel>>

    @Query("SELECT * from associated_label_table WHERE idLabel = :keyLabel")
    fun get(keyLabel: Int): LiveData<AssociatedLabel>

    @Query("SELECT * from associated_label_table WHERE idProduct = :key")
    fun getAllByProduct(key: Int): LiveData<List<AssociatedLabel>>

    @Query("DELETE FROM associated_label_table")
    fun clear()

}
