package com.example.atry

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PackagingViewModel: ViewModel() {

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _returnable = MutableLiveData<String>()
    val returnable: LiveData<String>
        get() = _returnable
    private val _reusable = MutableLiveData<String>()
    val reusable: LiveData<String>
        get() = _reusable
    private val _recyclable = MutableLiveData<String>()
    val recyclable: LiveData<String>
        get() = _recyclable
    private val _compostable = MutableLiveData<String>()
    val compostable: LiveData<String>
        get() = _compostable
    private val _rawMaterialsRecycled = MutableLiveData<String>()
    val rawMaterialsRecycled: LiveData<String>
        get() = _rawMaterialsRecycled
    private val _certificated = MutableLiveData<String>()
    val certificated: LiveData<String>
        get() = _certificated

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked

    init {
        _onNextButtonClicked.value = false
    }

    fun onDescriptionChange(e: Editable?) {
        Log.d("TAG: ", "Descripción: $e")
        _description.value = e.toString()
    }
    fun onReturnableChange(item: String) {
        Log.d("TAG: ", "Valor de Retornable: $item")
        _returnable.value = item
    }
    fun onReusableChange(item: String) {
        Log.d("TAG: ", "Valor de Reutilizable: $item")
        _reusable.value = item
    }
    fun onRecyclableChange(item: String) {
        Log.d("TAG: ", "Valor de Reciclable: $item")
        _recyclable.value = item
    }
    fun onCompostableChange(item: String) {
        Log.d("TAG: ", "Valor de Compostable: $item")
        _compostable.value = item
    }
    fun onRawMaterialsRecycledChange(item: String) {
        Log.d("TAG: ", "Valor de Materias primas: $item")
        _rawMaterialsRecycled.value = item
    }
    fun onCertificatedChange(item: String) {
        Log.d("TAG: ", "Valor de Certificated: $item")
        _certificated.value = item
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
        // TODO Keep values to update DB.
    }
}