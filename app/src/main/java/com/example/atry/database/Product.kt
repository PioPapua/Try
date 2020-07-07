package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "barcode")
    var barcode: String = "barcode",

    @ColumnInfo(name = "name")
    var name: String = "name",

    @ColumnInfo(name = "category")
    var category: String = "category",

    @ColumnInfo(name = "isFood")
    var isFood: Boolean = true,

    @ColumnInfo(name = "portionType")
    var portionType: String = "portionType",

    @ColumnInfo(name = "packaging")
    var packaging: Int = 0,

    @ColumnInfo(name = "manufacturer")
    var manufacturer: Int = 0,

    @ColumnInfo(name = "trademark")
    var trademark: String = "trademark",

    @ColumnInfo(name = "netWeight")
    var netWeight: String = "netWeight",

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "categoryType")
    var categoryType: String = "categoryType",

    @ColumnInfo(name = "user")
    var user: Int = 0,

    @ColumnInfo(name = "enabled")
    var enabled: Boolean = true
)