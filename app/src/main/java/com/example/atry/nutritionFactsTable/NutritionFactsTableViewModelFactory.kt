package com.example.atry.nutritionFactsTable

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.NutritionFactDao

// Provides the NutritionFactsDao and context to the ViewModel.
class NutritionFactsTableViewModelFactory(
    private val dataSource: NutritionFactDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionFactsTableViewModel::class.java)) {
            return NutritionFactsTableViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

