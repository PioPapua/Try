package com.example.atry.labelsTable

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.Label
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
}