package com.example.atry.nutritionFacts

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.NutritionFactDao

// Provides the NutritionFactDao and context to the ViewModel.
class NutritionFactsViewModelFactory(
    private val dataSource: NutritionFactDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionFactsViewModel::class.java)) {
            return NutritionFactsViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

