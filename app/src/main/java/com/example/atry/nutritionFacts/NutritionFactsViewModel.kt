package com.example.atry.nutritionFacts

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.*
import com.example.atry.network.ConzoomApi
import com.example.atry.network.NutritionFactData
import kotlinx.coroutines.*

class NutritionFactsViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {
    // Define parameters to communicate with NutritionFacts Fragment/Layout
    private val _calories = MutableLiveData<Float>()
    val calories: LiveData<Float>
        get() = _calories
    private val _carbohydrates = MutableLiveData<Float>()
    val carbohydrates: LiveData<Float>
        get() = _carbohydrates
    private val _proteins = MutableLiveData<Float>()
    val proteins: LiveData<Float>
        get() = _proteins
    private val _fatTotal = MutableLiveData<Float>()
    val fatTotal: LiveData<Float>
        get() = _fatTotal
    private val _fatSaturated = MutableLiveData<Float>()
    val fatSaturated: LiveData<Float>
        get() = _fatSaturated
    private val _fatTrans = MutableLiveData<Float>()
    val fatTrans: LiveData<Float>
        get() = _fatTrans
    private val _fiber = MutableLiveData<Float>()
    val fiber: LiveData<Float>
        get() = _fiber
    private val _sodium = MutableLiveData<Float>()
    val sodium: LiveData<Float>
        get() = _sodium

    // Buttons' functions
    // Button to associate all Nutrition Facts listed to our Product
    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked
    // Button to add one or more Nutrition Facts to our current list.
    private val _onAddNutritionFacts = MutableLiveData<Boolean>()
    val onAddNutritionFacts: LiveData<Boolean>
        get() = _onAddNutritionFacts

    // Empty the NutritionFactAssignment table on creating the view, so the user has to pick the values to be added.
    private val _onClearTable = MutableLiveData<Boolean>()
    val onClearTable: LiveData<Boolean>
        get() = _onClearTable

    private val _onSaveValuesComplete = MutableLiveData<Boolean>()
    val onSaveValuesComplete: LiveData<Boolean>
        get() = _onSaveValuesComplete

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var nutritionFacts: LiveData<List<NutritionFactAssignment>> = database.nutritionFactAssignmentDao.getAllNutritionFacts()
    var associatedNutritionFacts: LiveData<List<AssociatedNutrition>> = database.associatedNutritionDao.getAll()

    init {
        _onAddNutritionFacts.value = false
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
        _calories.value = 0F
        _carbohydrates.value = 0F
        _proteins.value = 0F
        _fatTotal.value = 0F
        _fatSaturated.value = 0F
        _fatTrans.value = 0F
        _fiber.value = 0F
        _sodium.value = 0F
        _onClearTable.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onCaloriesChange(e: Editable?){
        _calories.value = e?.toString()!!.toFloat()
    }
    fun onCarbohydratesChange(e: Editable?){
        _carbohydrates.value = e?.toString()!!.toFloat()
    }
    fun onProteinsChange(e: Editable?){
        _proteins.value = e?.toString()!!.toFloat()
    }
    fun onFatTotalChange(e: Editable?){
        _fatTotal.value = e?.toString()!!.toFloat()
    }
    fun onFatSaturatedChange(e: Editable?){
        _fatSaturated.value = e?.toString()!!.toFloat()
    }
    fun onFatTransChange(e: Editable?){
        _fatTrans.value = e?.toString()!!.toFloat()
    }
    fun onFiberChange(e: Editable?){
        _fiber.value = e?.toString()!!.toFloat()
    }
    fun onSodiumChange(e: Editable?){
        _sodium.value = e?.toString()!!.toFloat()
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
        _onSaveValuesComplete.value = false
        _onClearTable.value = false
    }

    fun onAddNutritionFactsClicked(){
        _onAddNutritionFacts.value = true
    }

    fun onAddNutritionFactsCompleted(){
        _onAddNutritionFacts.value = false
    }

    fun onClearTable() {
        uiScope.launch {
            withContext(Dispatchers.IO){
                database.nutritionFactAssignmentDao.clear()
                _onClearTable.postValue(true)
            }
        }
    }

    fun onValueChange(idNutritionFact: Int, textValue: String, idProduct: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val currentValue = database.associatedNutritionDao.get(idProduct, idNutritionFact)
                if (currentValue != null) {
                    currentValue.value = textValue.toFloat()
                    database.associatedNutritionDao.update(currentValue)
                    val productNutritionFacts = ConzoomApi.retrofitService.getProductPropertiesAsync(idProduct).await().nutritionFacts
                    for (item in productNutritionFacts){
                        if (item.nutritionFactData.id == idNutritionFact){
                            ConzoomApi.retrofitService.putAssociatedNutritionAsync(item.id, currentValue.value)
                        }
                    }
                } else {
                    if (textValue != "") {
                        val newValue = AssociatedNutrition()
                        newValue.idNutritionFact = idNutritionFact
                        newValue.idProduct = idProduct
                        newValue.value = textValue.toFloat()
                        database.associatedNutritionDao.insert(newValue)
                        val nutritionFact = database.nutritionFactDao.get(idNutritionFact)!!
                        val nutritionFactData = NutritionFactData(
                            id = nutritionFact.id,
                            description = nutritionFact.description,
                            dairyRecommendation = nutritionFact.dairyRecommendation,
                            portionType = nutritionFact.portionType,
                            informationLink = nutritionFact.informationLink,
                            name = nutritionFact.name
                        )
                        ConzoomApi.retrofitService.postAssociatedNutritionAsync(nutritionFactData, newValue.value)
                    }
                }
            }
        }
    }

    fun loadInitialValues(idProduct: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val nutritionFact = database.associatedNutritionDao.getAllByProduct(idProduct)
                if (nutritionFact != null){
                    for (item in nutritionFact){
                        when (database.nutritionFactDao.get(item.idNutritionFact)!!.name) {
                            "calorias" -> _calories.postValue(item.value)
                            "carbohidratos" -> _carbohydrates.postValue(item.value)
                            "proteinas" -> _proteins.postValue(item.value)
                            "grasas totales" -> _fatTotal.postValue(item.value)
                            "grasas saturadas" -> _fatSaturated.postValue(item.value)
                            "trasas trans" -> _fatTrans.postValue(item.value)
                            "fibra alimentaria" -> _fiber.postValue(item.value)
                            "sodio" -> _sodium.postValue(item.value)
                        }
                    }
                }
            }
        }
    }

    // Associate all mandatory and non mandatory values to Product's table in Room before navigating to next fragment.
    fun saveValues (idProduct: Int,
                    textCalories: String,
                    textCarbohydrates: String,
                    textProteins: String,
                    textTotalFat: String,
                    textSaturatedFat: String,
                    textTransFat: String,
                    textFiber: String,
                    textSodium: String){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val idCalories = database.nutritionFactDao.getNutritionFactIdByName("calorias")!!
                val idCarbohydrates = database.nutritionFactDao.getNutritionFactIdByName("carbohidratos")!!
                val idProteins = database.nutritionFactDao.getNutritionFactIdByName("proteinas")!!
                val idTotalFat = database.nutritionFactDao.getNutritionFactIdByName("grasas totales")!!
                val idSaturatedFat = database.nutritionFactDao.getNutritionFactIdByName("grasas saturadas")!!
                val idTransFat = database.nutritionFactDao.getNutritionFactIdByName("grasas trans")!!
                val idFiber = database.nutritionFactDao.getNutritionFactIdByName("fibra alimentaria")!!
                val idSodium = database.nutritionFactDao.getNutritionFactIdByName("sodio")!!
                onValueChange(idCalories, textCalories, idProduct)
                onValueChange(idCarbohydrates, textCarbohydrates, idProduct)
                onValueChange(idProteins, textProteins, idProduct)
                onValueChange(idTotalFat, textTotalFat, idProduct)
                onValueChange(idSaturatedFat, textSaturatedFat, idProduct)
                onValueChange(idTransFat, textTransFat, idProduct)
                onValueChange(idFiber, textFiber, idProduct)
                onValueChange(idSodium, textSodium, idProduct)

                val currentNutrients = database.associatedNutritionDao.getAllByProduct(idProduct)
                val nutrientsSet = mutableSetOf<AssociatedNutrition>()
                if (currentNutrients != null) {
                    for (item in currentNutrients) {
                        nutrientsSet.add(item)
                    }
                }
                val nutritionList = mutableListOf<String>()
                for (item in nutrientsSet){
                    nutritionList.add(item.idNutritionFact.toString())
                }

                val product = database.productDao.get(idProduct)
                product!!.nutrients = nutritionList.toList()
                database.productDao.update(product)
                _onSaveValuesComplete.postValue(true)
            }
        }
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
    }
}