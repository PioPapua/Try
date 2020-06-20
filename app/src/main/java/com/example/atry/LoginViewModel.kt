package com.example.atry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _eventLogin = MutableLiveData<Boolean>()
    val eventLogin: LiveData<Boolean>
        get() =_eventLogin

    init {
        _eventLogin.value = false
    }

    fun onLogin() {
        // TODO Authenticate the users
        _eventLogin.value = true
    }

    fun onLoginComplete() {
        _eventLogin.value = false // It states that the onLogin event has been already handled.
    }
}