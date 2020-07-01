package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the NutritionFact class with Room.

@Dao
interface NutritionFactDao {

    @Insert
    fun insert(fact: NutritionFact)

    @Update
    fun update(fact: NutritionFact)

    @Query("SELECT * from nutrition_fact_table WHERE id = :key")
    fun get(key: Int): NutritionFact?

    @Query("SELECT portionType from nutrition_fact_table WHERE id = :key")
    fun getPortionType(key: Int): String?

    @Query("DELETE FROM nutrition_fact_table")
    fun clear()

    @Query("SELECT * FROM nutrition_fact_table")
    fun getAllNutritionFacts(): LiveData<List<NutritionFact>>

}

