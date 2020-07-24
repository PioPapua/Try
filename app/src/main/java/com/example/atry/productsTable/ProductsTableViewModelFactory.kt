package com.example.atry.productsTable

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.ConzoomDatabase

// Provides the Database and context to the ViewModel.
class ProductsTableViewModelFactory(
    private val dataSource: ConzoomDatabase,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsTableViewModel::class.java)) {
            return ProductsTableViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}