package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.*

// Defines methods for using the NutritionFactAssignment class with Room.

@Dao
interface NutritionFactAssignmentDao {

    @Insert
    fun insert(fact: NutritionFactAssignment)

    @Update
    fun update(fact: NutritionFactAssignment)

    @Delete
    fun delete(fact: NutritionFactAssignment)

    @Query("SELECT * from nutrition_fact_assignment_table WHERE id = :key")
    fun get(key: Int): NutritionFactAssignment?

    @Query("SELECT portionType from nutrition_fact_assignment_table WHERE id = :key")
    fun getPortionType(key: Int): String?

    @Query("DELETE FROM nutrition_fact_assignment_table")
    fun clear()

    @Query("DELETE FROM nutrition_fact_assignment_table WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM nutrition_fact_assignment_table")
    fun getAllNutritionFacts(): LiveData<List<NutritionFactAssignment>>

}

