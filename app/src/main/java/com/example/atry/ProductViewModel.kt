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
    private val _portion_type = MutableLiveData<String>()
    val portionType: LiveData<String>
        get() = _portion_type
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

    init {
        _isFood.value = false
    }

    // Update data when user introduces an edition in Layout
    fun onNextButtonClicked(){
        // TODO Keep values to update DB. Call to next view.
    }
    fun onNameChange(e: Editable?){
        Log.d("TAG: ", "Valor de Nombre: ${e}")
        _name.value = e?.toString()
    }
    fun onCategoryChange(e: Editable?){
        Log.d("TAG: ", "Valor de Categoría: ${e}")
        _category.value = e?.toString()
    }
    fun onBarcodeChange(e: Editable?){
        Log.d("TAG: ", "Valor de Código de barras: ${e}")
        _barcode.value = e?.toString()
    }
    fun onPortionChange(e: Editable?){
        Log.d("TAG: ", "Valor de Porción: ${e}")
        _portion.value = e?.toString()
    }
//    fun onPortionTypeChange(parent: AdapterView<>, view: View, pos: Int, id: Long){
//        parent.setSelection(pos)
//    }
    fun onTrademarkChange(e: Editable?){
        Log.d("TAG: ", "Valor de Marca: ${e}")
        _trademark.value = e?.toString()
    }
    fun onNetWeightChange(e: Editable?){
        Log.d("TAG: ", "Valor de Peso Neto: ${e}")
        _netWeight.value = e?.toString()
    }
    fun onDescriptionChange(e: Editable?){
        Log.d("TAG: ", "Valor de Descripción: ${e}")
        _description.value = e?.toString()
    }
    fun onImageUrlChange(e: Editable?){
        Log.d("TAG: ", "Valor de Url de Imagen: ${e}")
        _imageUrl.value = e?.toString()
    }
    fun onIsFoodChange(){
        _isFood.value = _isFood.value?.not()
        Log.d("TAG: ", "Valor de Es alimento: ${_isFood.value}")
    }
}