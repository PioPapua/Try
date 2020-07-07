package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "associated_packaging_characteristic_table")
data class AssociatedPackagingCharacteristic(
    @PrimaryKey(autoGenerate = true)
    var idPackagingPackagingCharacteristic: Int = 0,
    @ColumnInfo(name = "idProduct")
    var idProduct: Int = 0,

    @ColumnInfo(name = "idPackaging")
    var idPackaging: Int = 0
)