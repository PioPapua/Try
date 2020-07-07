package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packaging_table")
data class Packaging(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "packagingType")
    var packagingType: String = "packagingType"
)