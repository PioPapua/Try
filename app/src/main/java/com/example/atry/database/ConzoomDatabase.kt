package com.example.atry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Ingredient::class, Label::class], // Define every table on the Database
    version = 15,
    exportSchema = false)

abstract class ConzoomDatabase : RoomDatabase() {

    abstract val ingredientDao: IngredientDao
    abstract val labelDao: LabelDao

    companion object {

        @Volatile
        private var INSTANCE: ConzoomDatabase? = null

        fun getInstance(context: Context): ConzoomDatabase {
            synchronized(this) { // Only one thread of execution can enter the block at a time.
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ConzoomDatabase::class.java,
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