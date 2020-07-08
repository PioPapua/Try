package com.example.atry.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atry.database.ConzoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LoginViewModel (val database: ConzoomDatabase, application: Application) : AndroidViewModel(application) {

    private val _eventLogin = MutableLiveData<Boolean>()
    val eventLogin: LiveData<Boolean>
        get() =_eventLogin

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        _eventLogin.value = false
    }

    fun onLogin(pass: String, usr: String) {
        // TODO Authenticate the users
        _eventLogin.value = true
    }

    fun onLoginComplete() {
        _eventLogin.value = false // It states that the onLogin event has been already handled.
    }
}