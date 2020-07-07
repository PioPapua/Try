package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "associated_ingredient_table")
data class AssociatedIngredient(
    @PrimaryKey(autoGenerate = true)
    var idProductIngredient: Int = 0,
    @ColumnInfo(name = "idProduct")
    var idProduct: Int = 0,

    @ColumnInfo(name = "idIngredient")
    var idIngredient: Int = 0
)