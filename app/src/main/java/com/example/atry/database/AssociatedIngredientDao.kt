package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the AssociatedIngredient class with Room.

@Dao
interface AssociatedIngredientDao {

    @Insert
    fun insert(ingredient: AssociatedIngredient)

    @Update
    fun update(ingredient: AssociatedIngredient)

    @Query("SELECT * from associated_ingredient_table")
    fun getAll(): LiveData<List<AssociatedIngredient>>

    @Query("SELECT * from associated_ingredient_table WHERE idIngredient = :keyIngredient")
    fun get(keyIngredient: Int): LiveData<AssociatedIngredient>

    @Query("SELECT * from associated_ingredient_table WHERE idProduct = :key")
    fun getAllByProduct(key: Int): LiveData<List<AssociatedIngredient>>

    @Query("DELETE FROM associated_ingredient_table")
    fun clear()

}
