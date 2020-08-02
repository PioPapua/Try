package com.example.atry.manufacturer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.Manufacturer
import kotlinx.coroutines.*

class ManufacturerViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

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

    fun saveValues(idProduct: Int, businessName: String) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                var manufacturer = database.manufacturerDao.getIdByName(businessName)
                if (manufacturer == null) {
                    manufacturer = Manufacturer()
                    manufacturer.name = businessName
                    database.manufacturerDao.insert(manufacturer)
                }

                val product = database.productDao.get(idProduct)
                product!!.manufacturer = manufacturer.name
                database.productDao.update(product)
                _onSaveValuesComplete.postValue(true)
            }
        }
    }

}