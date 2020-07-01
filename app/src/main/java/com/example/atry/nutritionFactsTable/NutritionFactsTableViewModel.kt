package com.example.atry.nutritionFactsTable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.NutritionFact
import com.example.atry.database.NutritionFactDao
import kotlinx.coroutines.*

class NutritionFactsTableViewModel (val database: NutritionFactDao, application: Application) : AndroidViewModel(application) {

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var nutritionFacts : LiveData<List<NutritionFact>> = database.getAllNutritionFacts()

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

