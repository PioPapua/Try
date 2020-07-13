package com.example.atry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    // Define every table on the Database
    entities = [Product::class,
        Ingredient::class,
        AssociatedIngredient::class,
        Label::class,
        AssociatedLabel::class,
        NutritionFact::class,
        AssociatedNutrition::class,
        PackagingDB::class,
        PackagingCharacteristic::class,
        NutritionFactAssignment::class,
        Manufacturer::class,
        User::class],
    version = 28,
    exportSchema = false)

@TypeConverters(Converters::class)

abstract class ConzoomDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
    abstract val ingredientDao: IngredientDao
    abstract val associatedIngredientDao: AssociatedIngredientDao
    abstract val labelDao: LabelDao
    abstract val associatedLabelDao: AssociatedLabelDao
    abstract val nutritionFactDao: NutritionFactDao
    abstract val associatedNutritionDao: AssociatedNutritionDao
    abstract val packagingDao: PackagingDao
    abstract val packagingCharacteristicDao: PackagingCharacteristicDao
    abstract val nutritionFactAssignmentDao: NutritionFactAssignmentDao
    abstract val manufacturerDao: ManufacturerDao
    abstract val userDao: UserDao

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