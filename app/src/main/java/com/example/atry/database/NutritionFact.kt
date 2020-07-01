package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_fact_table")
data class NutritionFact(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "name",

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "dairyRecommendation")
    var dairyRecommendation: Float = 0F,

    @ColumnInfo(name = "portionType")
    var portionType: String = "portion type",

    @ColumnInfo(name = "informationLink")
    var informationLink: String = "informationLink"
)

