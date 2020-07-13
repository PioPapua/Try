package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the Ingredient class with Room.

@Dao
interface IngredientDao {

    @Insert
    fun insert(ingredient: Ingredient)

    @Update
    fun update(ingredient: Ingredient)

    @Query("SELECT * from ingredient_table WHERE id = :key")
    fun get(key: Int): Ingredient?

    @Query("DELETE FROM ingredient_table")
    fun clear()

    @Query("SELECT * FROM ingredient_table")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient_table ORDER BY id DESC LIMIT 1")
    fun getLastIngredient(): Ingredient?

}