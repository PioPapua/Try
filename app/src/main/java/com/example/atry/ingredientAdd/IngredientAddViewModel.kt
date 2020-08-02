package com.example.atry.ingredientAdd

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.Ingredient
import com.example.atry.database.IngredientDao
import com.example.atry.network.ConzoomApi
import com.example.atry.network.IngredientData
import com.example.atry.network.Login
import kotlinx.coroutines.*

class IngredientAddViewModel (val database: IngredientDao, application: Application) : AndroidViewModel(application) {
    // Define parameters to communicate with IngredientAdd Fragment/Layout
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name
    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _informationLink = MutableLiveData<String>()
    val informationLink: LiveData<String>
        get() = _informationLink
    private val _categoryType = MutableLiveData<String>()
    val categoryType: LiveData<String>
        get() = _categoryType
    private val _warning = MutableLiveData<Boolean>()
    val warning: LiveData<Boolean>
        get() = _warning
    private val _epaClassification = MutableLiveData<String>()
    val epaClassification: LiveData<String>
        get() = _epaClassification
    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _warning.value = false
        _onAddButtonClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insert(ingredient: Ingredient) {
        withContext(Dispatchers.IO) {
            database.insert(ingredient)
            val ingredientData = IngredientData(
                name = ingredient.name,
                description = ingredient.description,
                informationLink = ingredient.informationLink,
                categoryType = ingredient.categoryType,
                warning = ingredient.warning,
                epaClassification = ingredient.epaClassification,
                id = ingredient.id
            )
            val deferredIngredientData = ConzoomApi.retrofitService.postIngredientAsync(ingredientData)
        }
    }
    private suspend fun update(ingredient: Ingredient) {
        withContext(Dispatchers.IO) {
            database.update(ingredient)
        }
    }

    fun onNameChange(e: Editable?){
        _name.value = e?.toString()
    }
    fun onDescriptionChange(e: Editable?){
        _description.value = e?.toString()
    }
    fun onInformationLinkChange(e: Editable?){
        _informationLink.value = e?.toString()
    }
    fun onEpaClassificationChange(item: String){
        _epaClassification.value = item
    }
    fun onCategoryTypeChange(item: String) {
        _categoryType.value = item
    }
    fun onWarningChange(){
        _warning.value = _warning.value?.not()
    }

    fun onNavigationCompleted(){
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        uiScope.launch {
            val newIngredient = Ingredient()
            newIngredient.name = _name.value.toString()
            newIngredient.description = _description.value.toString()
            newIngredient.informationLink = _informationLink.value.toString()
            newIngredient.epaClassification = _epaClassification.value.toString()
            newIngredient.warning = _warning.value!!
            newIngredient.categoryType = _categoryType.value.toString()
            insert(newIngredient)
        }
        _onAddButtonClicked.value = true
    }
}