package com.example.atry

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.IngredientDatabaseDao

class IngredientAddViewModel (val database: IngredientDatabaseDao, application: Application) : AndroidViewModel(application) {
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

    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    init {
        _warning.value = false
        _onAddButtonClicked.value = false
    }

    fun onNameChange(e: Editable?){
        Log.d("TAG: ", "Valor de Nombre: $e")
        _name.value = e?.toString()
    }
    fun onDescriptionChange(e: Editable?){
        Log.d("TAG: ", "Valor de Descripción: $e")
        _description.value = e?.toString()
    }
    fun onInformationLinkChange(e: Editable?){
        Log.d("TAG: ", "Valor de Link de información: $e")
        _informationLink.value = e?.toString()
    }
    fun onCategoryTypeChange(item: String) {
        Log.d("TAG: ", "Valor de Tipo de Rubro: $item")
        _categoryType.value = item
    }
    fun onWarningChange(){
        _warning.value = _warning.value?.not()
    }

    fun onNavigationCompleted(){
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        _onAddButtonClicked.value = true
        // TODO Keep values to update DB.
    }
}