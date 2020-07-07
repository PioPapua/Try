package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packaging_characteristic_table")
data class PackagingCharacteristic(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "category")
    var category: String = "category",

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "value")
    var value: Int = 0
)