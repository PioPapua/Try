package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_fact_assignment_table")
data class NutritionFactAssignment(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "name",

    @ColumnInfo(name = "portionType")
    var portionType: String = "portion type"
)

