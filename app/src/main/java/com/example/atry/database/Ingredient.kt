package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "name",

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "epaClassification")
    var epaClassification: String = "epaClassification",

    @ColumnInfo(name = "warning")
    var warning: Boolean = false,

    @ColumnInfo(name = "informationLink")
    var informationLink: String = "informationLink",

    @ColumnInfo(name = "categoryType")
    var categoryType: String = "category type"
)