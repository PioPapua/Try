package com.example.atry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ingredient::class], // Define every table on the Database
    version = 1,
    exportSchema = false)

abstract class Database : RoomDatabase() {

    abstract val ingredientDatabaseDao: IngredientDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: com.example.atry.database.Database? = null

        fun getInstance(context: Context): com.example.atry.database.Database {
            synchronized(this) { // Only one thread of execution can enter the block at a time.
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.atry.database.Database::class.java,
                        "conzoom_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}