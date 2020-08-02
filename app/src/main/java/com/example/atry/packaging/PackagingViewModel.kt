package com.example.atry.packaging

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.PackagingDB
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

                // Get current Package
                val packagingId = database.productDao.getPackagingIdByProductId(idProduct)
                var packaging = database.packagingDao.get(packagingId)!!.value

                // Create a MutableList of selected characteristics
                val characteristics = mutableListOf<String>()
                characteristics.add(returnableId.toString())
                characteristics.add(reusableId.toString())
                characteristics.add(recyclableId.toString())
                characteristics.add(compostableId.toString())
                characteristics.add(rawMaterialsRecycledId.toString())
                characteristics.add(certificatedId.toString())

                if (packaging == null) {
                    packaging = PackagingDB()
                    packaging.description = _description.value
                    packaging.packagingType = _packagingType.value
                    packaging.characteristics = characteristics.toList()
                    database.packagingDao.insert(packaging)
                } else {
                    packaging.description = _description.value!!
                    packaging.packagingType = _packagingType.value!!
                    packaging.characteristics = characteristics.toList()
                    database.packagingDao.update(packaging)
                }

                val product = database.productDao.get(idProduct)
                val packagingElement = PackagingElement()
                packagingElement.description = packaging.description ?: ""
                packagingElement.packagingType = packaging.packagingType ?: ""
                packagingElement.characteristics = packaging.characteristics.toString()
                product!!.packaging = packagingElement.toString()
                database.productDao.update(product)
                Log.d("TAG: ", "Product: ${database.productDao.get(idProduct)}")
                _onSaveValuesComplete.postValue(true)
            }
        }
    }
}

class PackagingElement {
    var description = ""
    var packagingType = ""
    var characteristics = ""

    @Override
    override fun toString(): String {
        return ("{\"descripcion\": $description, " +
                "\"codigoTipoEnvase\": $packagingType, " +
                "\"idsCaracteristicaEnvase\": $characteristics}"
                )
    }
}