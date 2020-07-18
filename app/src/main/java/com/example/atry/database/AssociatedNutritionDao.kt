package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the AssociatedNutrition class with Room.

@Dao
interface AssociatedNutritionDao {

    @Insert
    fun insert(fact: AssociatedNutrition)

    @Update
    fun update(fact: AssociatedNutrition)

    @Query("SELECT * from associated_nutrition_table")
    fun getAll(): LiveData<List<AssociatedNutrition>>

    @Query("SELECT * from associated_nutrition_table WHERE idProduct = :keyProduct AND idNutritionFact = :keyNutritionFact")
    fun get(keyProduct: Int, keyNutritionFact: Int): AssociatedNutrition?

    @Query("SELECT * from associated_nutrition_table WHERE idProduct = :key")
    fun getAllByProduct(key: Int): List<AssociatedNutrition>?

    @Query("SELECT * from associated_nutrition_table WHERE idNutritionFact = :key")
    fun getAllByNutritionFact(key: Int): List<AssociatedNutrition>

    @Query("DELETE FROM associated_nutrition_table")
    fun clear()

}
