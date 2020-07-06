package com.example.atry.nutritionFactsTable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.NutritionFact
import com.example.atry.database.NutritionFactAssignment
import kotlinx.coroutines.*

class NutritionFactsTableViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked
    private val _onNutritionFactClicked = MutableLiveData<Boolean>()
    val onNutritionFactClicked: LiveData<Boolean>
        get() = _onNutritionFactClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var nutritionFacts: LiveData<List<NutritionFact>> = database.nutritionFactDao.getAllNutritionFacts()

    init {
        _onNextButtonClicked.value = false
        _onAddButtonClicked.value = false
        _onNutritionFactClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onAdditionCompleted() {
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        _onAddButtonClicked.value = true
    }

    fun onNavigationCompleted() {
        _onNextButtonClicked.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }

    fun onNutritionFactClicked(id: Int, isChecked: Boolean) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val nutritionFact = database.nutritionFactDao.get(id)
                val nutritionFactAssign = NutritionFactAssignment()
                nutritionFactAssign.id = nutritionFact!!.id
                nutritionFactAssign.name = nutritionFact!!.name
                nutritionFactAssign.portionType = nutritionFact!!.portionType

                if (isChecked) {
                    if (database.nutritionFactAssignmentDao.get(id) == null){
                        database.nutritionFactAssignmentDao.insert(nutritionFactAssign)
                    }
                } else {
                    database.nutritionFactAssignmentDao.delete(nutritionFactAssign)
                }
            }
        }
        _onNutritionFactClicked.value = true
    }
}


