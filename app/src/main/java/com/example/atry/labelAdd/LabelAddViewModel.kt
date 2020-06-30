package com.example.atry.labelAdd

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.atry.database.Label
import com.example.atry.database.LabelDao
import kotlinx.coroutines.*

class LabelAddViewModel (val database: LabelDao, application: Application) : AndroidViewModel(application) {
    // Define parameters to communicate with LabelAdd Fragment/Layout
    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description
    private val _logoUrl = MutableLiveData<String>()
    val logoUrl: LiveData<String>
        get() = _logoUrl
    private val _categoryType = MutableLiveData<String>()
    val categoryType: LiveData<String>
        get() = _categoryType

    private val _onAddButtonClicked = MutableLiveData<Boolean>()
    val onAddButtonClicked: LiveData<Boolean>
        get() = _onAddButtonClicked

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _onAddButtonClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insert(label: Label) {
        withContext(Dispatchers.IO) {
            database.insert(label)
        }
    }
    private suspend fun update(label: Label) {
        withContext(Dispatchers.IO) {
            database.update(label)
        }
    }

    fun onDescriptionChange(e: Editable?){
        _description.value = e?.toString()
    }
    fun onLogoUrlChange(e: Editable?) {
        _logoUrl.value = e?.toString()
    }
    fun onCategoryTypeChange(item: String) {
        _categoryType.value = item
    }

    fun onNavigationCompleted(){
        _onAddButtonClicked.value = false
    }

    fun onAddButtonClicked() {
        uiScope.launch {
            val newLabel = Label()
            newLabel.description = _description.value.toString()
            newLabel.logoUrl = _logoUrl.value.toString()
            newLabel.categoryType = _categoryType.value.toString()
            insert(newLabel)
        }
        _onAddButtonClicked.value = true
    }
}