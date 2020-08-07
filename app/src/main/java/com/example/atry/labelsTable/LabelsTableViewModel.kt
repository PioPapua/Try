package com.example.atry.labelsTable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.Label
import com.example.atry.network.*
import kotlinx.coroutines.*

class LabelsTableViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

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
    var labels : LiveData<List<Label>> = database.labelDao.getAllLabels()
    private var currentLabels = mutableSetOf<String>()

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
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }

    fun onLabelClicked(id: Int, isChecked: Boolean) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val label = database.ingredientDao.get(id)
                if (isChecked) {
                    currentLabels.add(label!!.id.toString())
                } else {
                    currentLabels.remove(label!!.id.toString())
                }
            }
        }
    }

    fun onStoreProduct(idProduct: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val currentProduct = database.productDao.get(idProduct)!!
                val currentPackaging = database.packagingDao.get(currentProduct.packaging)!!

                val characteristics = mutableListOf<PackagingCharacteristicData>()
                for (item in stringListToIntList(currentPackaging.characteristics)){
                    val characteristic = database.packagingCharacteristicDao.get(item)!!
                    val characteristicData = PackagingCharacteristicData(
                        id = characteristic.id,
                        category = characteristic.category,
                        description = characteristic.description,
                        value = characteristic.value
                    )
                    characteristics.add(characteristicData)
                }

                val packaging = CompletePackaging(
                    idPackaging = currentPackaging.id,
                    description = currentPackaging.description ?: "",
                    packagingType = currentPackaging.packagingType ?: "",
                    characteristicData = characteristics
                )
                val currentNutrients = database.associatedNutritionDao.getAllByProduct(currentProduct.id)!!
                val nutrients = mutableListOf<ProductNutritionFactData>()
                for (item in currentNutrients){
                    val nutritionFact = database.nutritionFactDao.get(item.idNutritionFact)!!
                    val nutrient = ProductNutritionFactData(
                        value = item.value,
                        nutritionFactData = NutritionFactData(
                            name = nutritionFact.name,
                            description = nutritionFact.description,
                            dairyRecommendation = nutritionFact.dairyRecommendation,
                            portionType = nutritionFact.portionType,
                            informationLink = nutritionFact.informationLink,
                            id = nutritionFact.id
                        )
                    )
                    nutrients.add(nutrient)
                }

                val labels = mutableListOf<LabelData>()
                for (item in stringListToIntList(currentProduct.labels)){
                    val label = database.labelDao.get(item)!!
                    val labelData = LabelData(
                        id = label.id,
                        categoryType = label.categoryType,
                        description = label.description,
                        logoUrl = label.logoUrl
                    )
                    labels.add(labelData)
                }

                val ingredients = mutableListOf<IngredientData>()
                for (item in stringListToIntList(currentProduct.ingredients)){
                    val ingredient = database.ingredientDao.get(item)!!
                    val ingredientData = IngredientData(
                        id = ingredient.id,
                        description = ingredient.description,
                        informationLink = ingredient.informationLink,
                        categoryType = ingredient.categoryType,
                        warning = ingredient.warning,
                        epaClassification = ingredient.epaClassification,
                        name = ingredient.name
                    )
                    ingredients.add(ingredientData)
                }
                val manufacturer = ConzoomApi.retrofitService.getManufacturerByNameAsync(currentProduct.manufacturer).await().data
                val remoteProduct = ProductFinalData(
                    barcode = currentProduct.barcode,
                    name = currentProduct.name,
                    category = currentProduct.category,
                    isFood = currentProduct.isFood,
                    portion = currentProduct.portion.toInt(),
                    portionType = currentProduct.portionType,
                    trademark = currentProduct.trademark,
                    netWeight = currentProduct.netWeight,
                    description = currentProduct.description ?:"",
                    categoryType = currentProduct.categoryType,
                    imageUrl = currentProduct.imageUrl ?: "",
                    manufacturer = manufacturer,
                    packaging = packaging,
                    nutritionFacts = nutrients.toList(),
                    labels = labels.toList(),
                    ingredientsIds = ingredients.toList()
                )
//                ConzoomApi.retrofitService.postProductAsync(remoteProduct)
            }
        }
    }

    fun onSaveValues(idProduct: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val product = database.productDao.get(idProduct)
                val previousLabels = database.productDao.getAllLabelsByProductId(idProduct)
                val labels = mutableSetOf<String>()

                val listValues: List<String> = previousLabels.toString().split(",").map { it -> it.trim() }
                listValues.forEach { it ->
                    currentLabels.add(it)
                }

                var newLabel: String
                for (item in currentLabels){
                    newLabel = item.replace("[", "")
                    newLabel = newLabel.replace("]","")
                    labels.add(newLabel)
                }

                product!!.labels = labels.toList()
                database.productDao.update(product)
                _onSaveValuesComplete.postValue(true)
            }
        }
    }

    private fun stringListToIntList(list: List<String>): List<Int> {
        return list.
                first().
                removeSurrounding("[", "]").
                replace(" ", "").
                split(",").
                map { it.toInt() }
    }
}