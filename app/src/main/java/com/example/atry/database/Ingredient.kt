package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ingrediente")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = "name",

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "warning")
    var warning: String = "Warning",

    @ColumnInfo(name = "categoryType")
    var categoryType: String = "category type"
)