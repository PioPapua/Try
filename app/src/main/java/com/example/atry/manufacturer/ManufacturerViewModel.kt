package com.example.atry.manufacturer

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.network.ConzoomApi
import com.example.atry.network.ManufacturerData
import com.example.atry.network.ManufacturerName
import kotlinx.coroutines.*

class ManufacturerViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

    private val _businessName = MutableLiveData<String>()
    val businessName: LiveData<String>
        get() = _businessName
    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    private val _onSaveValuesComplete = MutableLiveData<Boolean>()
    val onSaveValuesComplete: LiveData<Boolean>
        get() = _onSaveValuesComplete

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }

    fun onBusinessNameChanged(e: Editable?){
        _businessName.value = e.toString()
    }

    fun setInitialValues(idProduct: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val manufacturer = database.productDao.get(idProduct)?.manufacturer
                _businessName.postValue(manufacturer)
            }
        }
    }

    fun saveValues(idProduct: Int, businessName: String) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val manufacturer = ConzoomApi.retrofitService.getManufacturerByNameAsync(businessName).await().data
                if (manufacturer.name != businessName) {
                    val manufacturerName = ManufacturerName(name=businessName)
                    ConzoomApi.retrofitService.postManufacturerAsync(manufacturerName)
                }

                val product = database.productDao.get(idProduct)
                product!!.manufacturer = businessName
                database.productDao.update(product)
                _onSaveValuesComplete.postValue(true)
            }
        }
    }

}