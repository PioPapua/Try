package com.example.atry.labelsTable

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.LabelDao

// Provides the LabelsDao and context to the ViewModel.
class LabelsTableViewModelFactory(
    private val dataSource: ConzoomDatabase,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabelsTableViewModel::class.java)) {
            return LabelsTableViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}