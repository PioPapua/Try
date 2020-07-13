package com.example.atry.ingredientsTable

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.IngredientDao

// Provides the IngredientDao and context to the ViewModel.
class IngredientsTableViewModelFactory(
    private val dataSource: ConzoomDatabase,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientsTableViewModel::class.java)) {
            return IngredientsTableViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}