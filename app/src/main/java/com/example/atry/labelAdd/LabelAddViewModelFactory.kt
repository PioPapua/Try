package com.example.atry.labelAdd


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.LabelDao

// Provides the IngredientDatabaseDao and context to the ViewModel.
class LabelAddViewModelFactory(
    private val dataSource: LabelDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabelAddViewModel::class.java)) {
            return LabelAddViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}