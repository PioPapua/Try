package com.example.atry.ingredientAdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.IngredientDao

// Provides the IngredientDatabaseDao and context to the ViewModel.
class IngredientAddViewModelFactory(
    private val dataSource: IngredientDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientAddViewModel::class.java)) {
            return IngredientAddViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}