package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "associated_nutrition_table")
data class AssociatedNutrition(
    @PrimaryKey(autoGenerate = true)
    var idProductNutrition: Int = 0,

    @ColumnInfo(name = "value")
    var value: Float = 0F,

    @ColumnInfo(name = "idProduct")
    var idProduct: Int = 0,

    @ColumnInfo(name = "idNutritionFact")
    var idNutritionFact: Int = 0
)
