package com.example.atry.ingredientsTable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.Ingredient
import kotlinx.coroutines.*

class IngredientsTableViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private val _onSaveValuesComplete = MutableLiveData<Boolean>()
    val onSaveValuesComplete: LiveData<Boolean>
        get() = _onSaveValuesComplete

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var ingredients : LiveData<List<Ingredient>> = database.ingredientDao.getAllIngredients()
    private val currentIngredients = mutableSetOf<String>()

    init {
        _onNextButtonClicked.value = false
        _onAddButtonClicked.value = false

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onAdditionCompleted(){
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        _onAddButtonClicked.value = true
    }

    fun onNavigationCompleted(){
        // TODO Save values
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }

    fun onIngredientClicked(id: Int, isChecked: Boolean, idProduct: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val ingredient = database.ingredientDao.get(id)
                if (isChecked) {
                    currentIngredients.add(ingredient!!.id.toString())
                } else {
                    currentIngredients.remove(ingredient!!.id.toString())
                }
            }
        }
    }

    fun onSaveValues(idProduct: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val product = database.productDao.get(idProduct)
                val previousIngredients = database.productDao.getAllIngredientsByProductId(idProduct)
                val ingredients = mutableListOf<String>()

                currentIngredients.addAll(previousIngredients)
                for (item in currentIngredients!!){
                    ingredients.add(item)
                }

                // TODO Associate ingredients with Product
//                product.value!!.ingredients = ingredients
//                database.productDao.update(product.value!!)
                _onSaveValuesComplete.postValue(true)
            }
        }
    }
}