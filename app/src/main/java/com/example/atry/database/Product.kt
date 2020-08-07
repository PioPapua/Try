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

    @ColumnInfo(name = "portion")
    var portion: String = "portion",

    @ColumnInfo(name = "portionType")
    var portionType: String = "portionType",

    @ColumnInfo(name = "packaging")
    var packaging: Int = -1, // Integer with the only ID corresponding to the packaging

    @ColumnInfo(name = "manufacturer")
    var manufacturer: String = "", // String that contains the name of the manufacturer

    @ColumnInfo(name = "trademark")
    var trademark: String = "trademark",

    @ColumnInfo(name = "netWeight")
    var netWeight: String = "netWeight",

    @ColumnInfo(name = "description")
    var description: String? = "description",

    @ColumnInfo(name = "categoryType")
    var categoryType: String = "categoryType",

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = "imageUrl",

    @ColumnInfo(name = "ingredients")
    var ingredients: List<String> = listOf(), // Save only ingredients IDs

    @ColumnInfo(name = "nutrients")
    var nutrients: List<String> = listOf(), // Save only nutrients IDs

    @ColumnInfo(name = "labels")
    var labels: List<String> = listOf(), // Save only labels IDs

    @ColumnInfo(name = "user")
    var user: Int = 0,

    @ColumnInfo(name = "enabled")
    var enabled: Boolean = true
)