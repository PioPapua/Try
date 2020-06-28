package com.example.atry.manufacturer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManufacturerViewModel : ViewModel() {

    private val _businessName = MutableLiveData<String>()
    val businessName: LiveData<String>
        get() = _businessName

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked

    init {
        _onNextButtonClicked.value = false
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
        // TODO Keep values to update DB.
    }

}