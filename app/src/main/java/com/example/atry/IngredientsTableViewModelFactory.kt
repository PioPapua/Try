package com.example.atry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.IngredientDatabaseDao

// Provides the IngredientDatabaseDao and context to the ViewModel.
class IngredientsTableViewModelFactory(
    private val dataSource: IngredientDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientsTableViewModel::class.java)) {
            return IngredientsTableViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}