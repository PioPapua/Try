package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "associated_label_table")
data class AssociatedLabel(
    @PrimaryKey(autoGenerate = true)
    var idProductLabel: Int = 0,
    @ColumnInfo(name = "idProduct")
    var idProduct: Int = 0,

    @ColumnInfo(name = "idLabel")
    var idLabel: Int = 0
)