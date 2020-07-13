package com.example.atry.product

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.Product
import kotlinx.coroutines.*

class ProductViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int>
        get() = _id
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
    private val _onSaveValuesCompleted = MutableLiveData<Boolean>()
    val onSaveValuesCompleted: LiveData<Boolean>
        get() = _onSaveValuesCompleted

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var currentProduct = Product()


    init {
        _isFood.value = false
        _onNextButtonClicked.value = false
        _onSaveValuesCompleted.value = false
    }

    fun onBarcodeReceived(barcode: String) {
        _barcode.value = barcode
    }

    // Update data when user introduces an edition in Layout
    fun onNameChange(e: Editable?){
        _name.value = e?.toString()
    }
    fun onCategoryChange(e: Editable?){
        _category.value = e?.toString()
    }
    fun onCategoryTypeChange(item: String) {
        _categoryType.value = item
    }
    fun onBarcodeChange(e: Editable?){
        _barcode.value = e?.toString()
    }
    fun onPortionChange(e: Editable?){
        _portion.value = e?.toString()
    }
    fun onPortionTypeChange(item: String) {
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
        _onSaveValuesCompleted.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
        // TODO Keep values to update DB.
    }

    fun saveValues(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                currentProduct.name = _name.value!!
                currentProduct.barcode = _barcode.value!!
                currentProduct.category = _category.value!!
                currentProduct.categoryType = _categoryType.value!!
                currentProduct.portion = _portion.value!!
                currentProduct.portionType = _portionType.value!!
                currentProduct.trademark = _trademark.value!!
                currentProduct.netWeight = _netWeight.value!!
                currentProduct.description = _description.value
                currentProduct.imageUrl = _imageUrl.value
                currentProduct.isFood = _isFood.value!!

                if (database.productDao.get(currentProduct.id) == null){
                    database.productDao.insert(currentProduct)
                } else {
                    database.productDao.update(currentProduct)
                }
                _id.postValue(currentProduct.id!!)
                _onSaveValuesCompleted.postValue(true)
            }
        }
    }

    fun setInitialValues(barcode: String){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                var previousProduct = database.productDao.getProductByBarcode(barcode)
                if (previousProduct != null) {
                    _name.postValue(previousProduct!!.name)
                    _category.postValue(previousProduct!!.category)
                    _categoryType.postValue(previousProduct!!.categoryType)
                    _portion.postValue(previousProduct!!.portion)
                    _portionType.postValue(previousProduct!!.portionType)
                    _trademark.postValue(previousProduct!!.trademark)
                    _netWeight.postValue(previousProduct!!.netWeight)
                    _description.postValue(previousProduct?.description)
                    _imageUrl.postValue(previousProduct?.imageUrl)
                    _isFood.postValue(previousProduct!!.isFood)
                } else {
                    previousProduct = Product()
                }
                currentProduct = previousProduct!!
            }
        }
    }
}