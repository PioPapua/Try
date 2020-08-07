package com.example.atry.packaging

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.PackagingDB
import com.example.atry.network.ConzoomApi
import kotlinx.coroutines.*

class PackagingViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application)  {

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _packagingType = MutableLiveData<String>()
    val packagingType: LiveData<String>
        get() = _packagingType
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
    private val _onSaveValuesComplete = MutableLiveData<Boolean>()
    val onSaveValuesComplete: LiveData<Boolean>
        get() = _onSaveValuesComplete

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
    }

    fun onDescriptionChange(e: Editable?) {
        _description.value = e.toString()
    }
    fun onPackagingTypeChange(e: Editable?) {
        _packagingType.value = e.toString()
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
        _onSaveValuesComplete.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }

    fun saveValues(idProduct: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                // Get Packaging Characteristics
                val characteristicDao = database.packagingCharacteristicDao
                val returnableId = characteristicDao.getIdByCategoryAndDescription("Retornable", _returnable.value!!)
                val reusableId = characteristicDao.getIdByCategoryAndDescription("Reutilizable", _reusable.value!!)
                val recyclableId = characteristicDao.getIdByCategoryAndDescription("Reciclable", _recyclable.value!!)
                val compostableId = characteristicDao.getIdByCategoryAndDescription("Compostable", _compostable.value!!)
                val rawMaterialsRecycledId = characteristicDao.getIdByCategoryAndDescription("Materias Primas Recicladas", _rawMaterialsRecycled.value!!)
                val certificatedId = characteristicDao.getIdByCategoryAndDescription("Certificacion Origen Materias Primas", _certificated.value!!)

                // Get current Packaging
                val packagingId = database.productDao.getPackagingIdByProductId(idProduct) // Returns -1 if no packaging is associated
                Log.d("TAG: ", "Packaging ID associated with current Product: $packagingId")
                var packaging = database.packagingDao.get(packagingId)
                Log.d("TAG: ", "Packaging associated with current Product: $packaging")

                // Create a MutableList of selected characteristics
                val characteristics = mutableListOf<String>()
                characteristics.add(returnableId.toString())
                characteristics.add(reusableId.toString())
                characteristics.add(recyclableId.toString())
                characteristics.add(compostableId.toString())
                characteristics.add(rawMaterialsRecycledId.toString())
                characteristics.add(certificatedId.toString())

                // Create or update PackagingDB
                if (packaging == null) {
                    packaging = PackagingDB()
                    packaging.description = _description.value
                    packaging.packagingType = _packagingType.value
                    packaging.characteristics = characteristics.toList()
                    database.packagingDao.insert(packaging)
                    val newPackaging = database.packagingDao.get(packagingId)
                    Log.d("TAG: ", "Packaging is new: $packaging")
                    Log.d("TAG: ", "Packaging in database: ${newPackaging}")
                } else {
                    packaging.description = _description.value!!
                    packaging.packagingType = _packagingType.value!!
                    packaging.characteristics = characteristics.toList()
                    database.packagingDao.update(packaging)
                    Log.d("TAG: ", "Packaging already existed: $packaging")
                    Log.d("TAG: ", "Packaging in database: ${database.packagingDao.get(0)}")
                }

                // Associate packagingId with Product
                val product = database.productDao.get(idProduct)!!
                Log.d("TAG: ", "Product in database: ${product}")
                product.packaging = packaging.id
                database.productDao.update(product)
                Log.d("TAG: ", "Product: ${database.productDao.get(idProduct)}")
                _onSaveValuesComplete.postValue(true)
            }
        }
    }

    fun setInitialValues(id: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val product = database.productDao.get(id)!!
                val packagingDB = database.packagingDao.get(product.packaging)

                if (packagingDB != null) {
                    _description.postValue(packagingDB.description)
                    _packagingType.postValue(packagingDB.packagingType)
                }
                try {
                    val packagingCharacteristic =
                        ConzoomApi.retrofitService.getPackagingCharacteristicsByPackageIdAsync(product.packaging)
                            .await()
                    for (item in packagingCharacteristic.data) {
                        when (item.category) {
                            "Retornable" -> onReturnableChange(item.description)
                            "Reutilizable" -> onReusableChange(item.description)
                            "Reciclable" -> onRecyclableChange(item.description)
                            "Compostable" -> onCompostableChange(item.description)
                            "Materias Primas Recicladas" -> onRawMaterialsRecycledChange(item.description)
                            "Certificacion Origen Materias Primas" -> _certificated.postValue(item.description)
                        }
                    }
                } catch (e: Exception){
                    Log.d("TAG: ", "Package not associated yet")
                }
            }
        }
    }
}