package com.example.atry.ingredientsTable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.IngredientDatabaseDao
import android.util.Log
import com.example.atry.database.Ingredient
import kotlinx.coroutines.*
import java.io.IOException

class IngredientsTableViewModel (val database: IngredientDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var ingredients : LiveData<List<Ingredient>> = database.getAllIngredients()

    init {
        _onNextButtonClicked.value = false
        _onAddButtonClicked.value = false
        prueba()
        Log.d("TAG: ", "ingredientes al comenzar: ${ingredients?.value}")
    }

    private fun prueba() {
        uiScope.launch {
            getAllIngredients()
        }
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

    private suspend fun getAllIngredients() {
        withContext(Dispatchers.IO) {
            try {
                ingredients = database.getAllIngredients()
            }
            catch (e: IOException){
                e.printStackTrace()
            }
            Log.d("TAG: ", "ingredientes al terminar: ${ingredients?.value}")
            Log.d("TAG: ", "ingrediente con id 1: ${database.get(1)}")
        }
    }
}