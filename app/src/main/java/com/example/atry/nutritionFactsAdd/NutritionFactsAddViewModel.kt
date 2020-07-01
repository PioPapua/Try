package com.example.atry.nutritionFactsAdd

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.NutritionFact
import com.example.atry.database.NutritionFactDao
import kotlinx.coroutines.*

class NutritionFactsAddViewModel (val database: NutritionFactDao, application: Application) : AndroidViewModel(application) {
    // Define parameters to communicate with LabelAdd Fragment/Layout
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name
    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _dairyRecommendation = MutableLiveData<String>()
    val dairyRecommendation: LiveData<String>
        get() = _dairyRecommendation
    private val _portionType = MutableLiveData<String>()
    val portionType: LiveData<String>
        get() = _portionType
    private val _informationLink = MutableLiveData<String>()
    val informationLink: LiveData<String>
        get() = _informationLink

    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _onAddButtonClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insert(nutritionFact: NutritionFact) {
        withContext(Dispatchers.IO) {
            database.insert(nutritionFact)
        }
    }
    private suspend fun update(nutritionFact: NutritionFact) {
        withContext(Dispatchers.IO) {
            database.update(nutritionFact)
        }
    }

    fun onNameChange(e: Editable?) {
        _name.value = e?.toString()
    }
    fun onDescriptionChange(e: Editable?){
        _description.value = e?.toString()
    }
    fun onDairyRecommendationChange(e: Editable?) {
        _dairyRecommendation.value = e.toString()
    }
    fun onPortionTypeChange(item: String) {
        _portionType.value = item
    }
    fun onInformationLinkChange(e: Editable?) {
        _informationLink.value = e?.toString()
    }

    fun onNavigationCompleted(){
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        uiScope.launch {
            val newNutritionFact = NutritionFact()
            newNutritionFact.name = _name.value.toString()
            newNutritionFact.description = _description.value.toString()
            newNutritionFact.dairyRecommendation = _dairyRecommendation.value!!.toFloat()
            newNutritionFact.portionType = _portionType.value.toString()
            newNutritionFact.informationLink = _informationLink.value.toString()
            insert(newNutritionFact)
        }
        _onAddButtonClicked.value = true
    }
}

