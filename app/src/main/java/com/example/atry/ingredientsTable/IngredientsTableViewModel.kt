package com.example.atry.ingredientsTable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.IngredientDatabaseDao
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import com.example.atry.database.Ingredient
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Thread.sleep

class IngredientsTableViewModel (val database: IngredientDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var ingredients : LiveData<List<Ingredient>> = database.getAllIngredients()

    init {
        _onNextButtonClicked.value = false
        _onAddButtonClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onAdditionCompleted(){
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        _onAddButtonClicked.value = true
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }
}