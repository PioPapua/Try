package com.example.atry.packaging

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atry.database.ConzoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PackagingViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application)  {

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

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _onNextButtonClicked.value = false
    }

    fun onDescriptionChange(e: Editable?) {
        _description.value = e.toString()
    }
    fun onReturnableChange(item: String) {
        _returnable.value = item
    }
    fun onReusableChange(item: String) {
        _reusable.value = item
    }
    fun onRecyclableChange(item: String) {
        _recyclable.value = item
    }
    fun onCompostableChange(item: String) {
        _compostable.value = item
    }
    fun onRawMaterialsRecycledChange(item: String) {
        _rawMaterialsRecycled.value = item
    }
    fun onCertificatedChange(item: String) {
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