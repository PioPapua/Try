package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the Ingredient class with Room.

@Dao
interface LabelDao {

    @Insert
    fun insert(label: Label)

    @Update
    fun update(label: Label)

    @Query("SELECT * from label_table WHERE id = :key")
    fun get(key: Long): Label?

    @Query("DELETE FROM label_table")
    fun clear()

    @Query("SELECT * FROM label_table")
    fun getAllLabels(): LiveData<List<Label>>

    @Query("SELECT * FROM label_table ORDER BY id DESC LIMIT 1")
    fun getLastLabel(): Label?

}