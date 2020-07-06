package com.example.atry.manufacturer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.ConzoomDatabase

// Provides the ConzoomDatabase and context to the ViewModel.
class ManufacturerViewModelFactory(
    private val dataSource: ConzoomDatabase,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManufacturerViewModel::class.java)) {
            return ManufacturerViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

