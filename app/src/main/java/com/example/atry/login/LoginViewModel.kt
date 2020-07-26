package com.example.atry.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atry.database.ConzoomDatabase
import com.example.atry.database.User
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

    fun onLogin(pass: String, usr: String) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val deferredLoginProperties = ConzoomApi.retrofitService.getLoginProperties(Login(usr, pass))
                try {
                    var user = User()
                    user.username = deferredLoginProperties.await().data.username
                    if (database.userDao.getUserByUsername(user.username) == null)
                        database.userDao.insert(user)
                    else user = database.userDao.getUserByUsername(user.username)!!
                    _eventLogin.postValue(true)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    fun onLoginComplete() {
        _eventLogin.value = false // It states that the onLogin event has been already handled.
    }
}