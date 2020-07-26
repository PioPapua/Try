package com.example.atry.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String = "username",

    @ColumnInfo(name = "password")
    var password: String? = "password"
)