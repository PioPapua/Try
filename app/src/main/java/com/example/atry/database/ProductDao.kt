package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the Product class with Room.

@Dao
interface ProductDao {

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("SELECT * from product_table")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT * from product_table WHERE id = :key")
    fun get(key: Int): Product?

    @Query("SELECT * from product_table WHERE barcode = :key")
    fun getProductByBarcode(key: String): Product?

    @Query("SELECT packaging from product_table WHERE id = :key")
    fun getPackagingIdByProductId(key: Int): Int

    @Query("SELECT ingredients from product_table WHERE id = :key")
    fun getAllIngredientsByProductId(key: Int): List<String>

    @Query("SELECT labels from product_table WHERE id = :key")
    fun getAllLabelsByProductId(key: Int): List<String>

    @Query("DELETE FROM product_table")
    fun clear()
}