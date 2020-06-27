package com.example.atry

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name
    private val _category = MutableLiveData<String>()
    val category: LiveData<String>
        get() = _category
    private val _categoryType = MutableLiveData<String>()
    val categoryType: LiveData<String>
        get() = _categoryType
    private val _barcode = MutableLiveData<String>()
    val barcode: LiveData<String>
        get() = _barcode
    private val _portion = MutableLiveData<String>()
    val portion: LiveData<String>
        get() = _portion
    private val _portionType = MutableLiveData<String>()
    val portionType: LiveData<String>
        get() = _portionType
    private val _trademark = MutableLiveData<String>()
    val trademark: LiveData<String>
        get() = _trademark
    private val _netWeight = MutableLiveData<String>()
    val netWeight: LiveData<String>
        get() = _netWeight
    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl
    private val _isFood = MutableLiveData<Boolean>()
    val isFood: LiveData<Boolean>
        get() = _isFood

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked

    init {
        _isFood.value = false
        _onNextButtonClicked.value = false
    }

    fun onBarcodeReceived(barcode: String) {
        _barcode.value = barcode
    }

    // Update data when user introduces an edition in Layout
    fun onNameChange(e: Editable?){
        Log.d("TAG: ", "Valor de Nombre: $e")
        _name.value = e?.toString()
    }
    fun onCategoryChange(e: Editable?){
        _category.value = e?.toString()
    }
    fun onCategoryTypeChange(item: String) {
        Log.d("TAG: ", "Valor de Tipo de Categoría: $item")
        _categoryType.value = item
    }
    fun onBarcodeChange(e: Editable?){
        _barcode.value = e?.toString()
    }
    fun onPortionChange(e: Editable?){
        _portion.value = e?.toString()
    }
    fun onPortionTypeChange(item: String) {
        Log.d("TAG: ", "Valor de Tipo de Porción: $item")
        _portionType.value = item
    }
    fun onTrademarkChange(e: Editable?){
        _trademark.value = e?.toString()
    }
    fun onNetWeightChange(e: Editable?){
        _netWeight.value = e?.toString()
    }
    fun onDescriptionChange(e: Editable?){
        _description.value = e?.toString()
    }
    fun onImageUrlChange(e: Editable?){
        _imageUrl.value = e?.toString()
    }
    fun onIsFoodChange(){
        _isFood.value = _isFood.value?.not()
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
        // TODO Keep values to update DB.
    }
}