package com.example.atry.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.*
import com.example.atry.network.ConzoomApi
import com.example.atry.network.Login
import kotlinx.coroutines.*
import java.lang.Exception

class LoginViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

    private val _eventLogin = MutableLiveData<Boolean>()
    val eventLogin: LiveData<Boolean>
        get() =_eventLogin

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _eventLogin.value = false
    }

    fun loadNutritionFacts(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val nutritionFacts = ConzoomApi.retrofitService.getNutritionFactsAsync().await()
                for (item in nutritionFacts.data){
                    val nutritionFact = NutritionFact()
                    nutritionFact.id = item.id
                    nutritionFact.name = item.name
                    nutritionFact.description = item.description
                    nutritionFact.informationLink = item.informationLink
                    nutritionFact.dairyRecommendation = item.dairyRecommendation
                    nutritionFact.portionType = item.portionType
                    if (database.nutritionFactDao.getNutritionFactIdByName(nutritionFact.name) == null){
                        database.nutritionFactDao.insert(nutritionFact)
                    } else {
                        Log.d("TAG: ", "El nutriente ya existe")
                    }
                }
            }
        }
    }

    fun loadLabels(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val labels = ConzoomApi.retrofitService.getLabelsAsync().await()
                for (item in labels.data){
                    val label = Label()
                    label.id = item.id
                    label.description = item.description
                    label.logoUrl = item.logoUrl
                    label.categoryType = item.categoryType
                    if (database.labelDao.get(label.id) == null){
                        database.labelDao.insert(label)
                    } else {
                        Log.d("TAG: ", "La etiqueta o extra ya existe")
                    }
                }
            }
        }
    }

    fun loadPackagingCharacteristics(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val packagingCharacteristics = ConzoomApi.retrofitService.getPackagingCharacteristicsAsync().await()
                for (item in packagingCharacteristics.data){
                    val packagingCharacteristic = PackagingCharacteristic()
                    packagingCharacteristic.id = item.id
                    packagingCharacteristic.description = item.description
                    packagingCharacteristic.value = item.value
                    packagingCharacteristic.category = item.category
                    if (database.packagingCharacteristicDao.get(packagingCharacteristic.id) == null){
                        database.packagingCharacteristicDao.insert(packagingCharacteristic)
                    } else {
                        Log.d("TAG: ", "La característica de envase ya existe")
                    }
                }
            }
        }
    }

    fun loadIngredients(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val ingredients = ConzoomApi.retrofitService.getIngredientsAsync().await()
                for (item in ingredients.data){
                    val ingredient = Ingredient()
                    ingredient.id = item.id
                    ingredient.name = item.name
                    ingredient.description = item.description
                    ingredient.informationLink = item.informationLink
                    ingredient.epaClassification = item.epaClassification
                    ingredient.categoryType = item.categoryType
                    if (database.ingredientDao.get(ingredient.id) == null){
                        database.ingredientDao.insert(ingredient)
                    } else {
                        Log.d("TAG: ", "El ingrediente ya existe")
                    }
                }
            }
        }
    }

    fun loadProducts(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val products = ConzoomApi.retrofitService.getProductsPropertiesAsync().await()
                for (item in products.data){
                    val product = Product()
                    product.barcode = item.barcode
                    product.name = item.name
                    product.category = item.category
                    product.categoryType = item.categoryType
                    product.isFood = item.isFood
                    product.portionType = item.portionType
                    product.portion = item.portion.toString()
                    product.trademark = item.trademark
                    product.netWeight = item.netWeight
                    product.description = item.description
                    product.imageUrl = item.imageUrl
                    product.manufacturer = item.manufacturer.name
                    product.enabled = item.enabled
                    product.packaging = item.packaging.idPackaging.toString()
                    if (database.productDao.getProductByBarcode(item.barcode) == null) {
                        database.productDao.insert(product)
                    } else {
                        Log.d("TAG: ", "El producto ya existe")
                    }
                }
            }
        }
    }

    fun onLogin(pass: String, usr: String) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val deferredLoginProperties = ConzoomApi.retrofitService.getLoginPropertiesAsync(Login(usr, pass))
                var user = User()
                user.username = deferredLoginProperties.await().data.username
                if (database.userDao.getUserByUsername(user.username) == null) {
                    database.userDao.insert(user)
                }
                else user = database.userDao.getUserByUsername(user.username)!!
                _eventLogin.postValue(true)
            }
        }
    }

    fun onLoginComplete() {
        _eventLogin.value = false // It states that the onLogin event has been already handled.
    }
}