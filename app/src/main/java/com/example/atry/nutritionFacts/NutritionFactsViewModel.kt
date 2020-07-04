package com.example.atry.nutritionFacts

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.*
import kotlinx.coroutines.*

class NutritionFactsViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {
    // Define parameters to communicate with NutritionFacts Fragment/Layout
    private val _calories = MutableLiveData<Int>()
    val calories: LiveData<Int>
        get() = _calories
    private val _carbohydrates = MutableLiveData<Int>()
    val carbohydrates: LiveData<Int>
        get() = _carbohydrates
    private val _proteins = MutableLiveData<Int>()
    val proteins: LiveData<Int>
        get() = _proteins
    private val _fatTotal = MutableLiveData<Int>()
    val fatTotal: LiveData<Int>
        get() = _fatTotal
    private val _fatSaturated = MutableLiveData<Int>()
    val fatSaturated: LiveData<Int>
        get() = _fatSaturated
    private val _fatTrans = MutableLiveData<Int>()
    val fatTrans: LiveData<Int>
        get() = _fatTrans
    private val _fiber = MutableLiveData<Int>()
    val fiber: LiveData<Int>
        get() = _fiber
    private val _sodium = MutableLiveData<Int>()
    val sodium: LiveData<Int>
        get() = _sodium

    // Buttons' functions
    // Button to associate all Nutrition Facts listed to our Product
    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    // Button to add one or more Nutrition Facts to our current list.
    private val _onAddNutritionFacts = MutableLiveData<Boolean>()
    val onAddNutritionFacts: LiveData<Boolean>
        get() = _onAddNutritionFacts

    // Empty the NutritionFactAssignment table on creating the view, so the user has to pick the values to be added.
    private val _onClearTable = MutableLiveData<Boolean>()
    val onClearTable: LiveData<Boolean>
        get() = _onClearTable

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var nutritionFacts: LiveData<List<NutritionFactAssignment>> = database.nutritionFactAssignmentDao.getAllNutritionFacts()

    init {
        _onAddNutritionFacts.value = false
        _onNextButtonClicked.value = false
        _calories.value = 0
        _carbohydrates.value = 0
        _proteins.value = 0
        _fatTotal.value = 0
        _fatSaturated.value = 0
        _fatTrans.value = 0
        _fiber.value = 0
        _sodium.value = 0
        _onClearTable.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onCaloriesChange(e: Editable?){
        _calories.value = e?.toString()!!.toInt()
    }
    fun onCarbohydratesChange(e: Editable?){
        _carbohydrates.value = e?.toString()!!.toInt()
    }
    fun onProteinsChange(e: Editable?){
        _proteins.value = e?.toString()!!.toInt()
    }
    fun onFatTotalChange(e: Editable?){
        _fatTotal.value = e?.toString()!!.toInt()
    }
    fun onFatSaturatedChange(e: Editable?){
        _fatSaturated.value = e?.toString()!!.toInt()
    }
    fun onFatTransChange(e: Editable?){
        _fatTrans.value = e?.toString()!!.toInt()
    }
    fun onFiberChange(e: Editable?){
        _fiber.value = e?.toString()!!.toInt()
    }
    fun onSodiumChange(e: Editable?){
        _sodium.value = e?.toString()!!.toInt()
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
        _onClearTable.value = false
    }

    fun onAddNutritionFactsClicked(){
        _onAddNutritionFacts.value = true
    }

    fun onAddNutritionFactsCompleted(){
        _onAddNutritionFacts.value = false
    }

    fun onClearTable() {
        uiScope.launch {
            withContext(Dispatchers.IO){
                database.nutritionFactAssignmentDao.clear()
                _onClearTable.postValue(true)
            }
        }
    }

    fun onCustomValueChange(idNutritionFact: Int, text: String, idProduct: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val currentValue = database.associatedNutritionDao.get(idProduct, idNutritionFact).value
                if (currentValue != null) {
                    database.associatedNutritionDao.update(currentValue)
                } else {
                    if (text != "") {
                        val newValue = AssociatedNutrition()
                        newValue.idNutritionFact = idNutritionFact
                        newValue.idProduct = idProduct
                        newValue.value = text.toFloat()
                        database.associatedNutritionDao.insert(newValue)
                    }
                }
            }
        }
    }

    fun onNextButtonClicked() {
//        uiScope.launch TODO Associate with DB
        _onNextButtonClicked.value = true
    }
}