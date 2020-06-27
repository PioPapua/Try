package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the Ingredient class with Room.

@Dao
interface IngredientDatabaseDao {

    @Insert
    fun insert(ingredient: Ingredient)

    @Update
    fun update(ingredient: Ingredient)

    @Query("SELECT * from ingrediente WHERE id = :key")
    fun get(key: Long): Ingredient?

    @Query("DELETE FROM ingrediente")
    fun clear()

    @Query("SELECT * FROM ingrediente ORDER BY id DESC")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingrediente ORDER BY id DESC LIMIT 1")
    fun getLastIngredient(): Ingredient?

}