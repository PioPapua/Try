package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "label_table")
data class Label(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "description")
    var description: String = "description",

    @ColumnInfo(name = "logoUrl")
    var logoUrl: String = "logoUrl",

    @ColumnInfo(name = "categoryType")
    var categoryType: String = "category type"
)