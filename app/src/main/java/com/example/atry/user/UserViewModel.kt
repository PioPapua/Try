package com.example.atry.user

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName
    private val _userSurname = MutableLiveData<String>()
    val userSurname: LiveData<String>
        get() = _userName

    private val _onNextButtonClicked = MutableLiveData<Boolean>()
    val onNextButtonClicked: LiveData<Boolean>
        get() = _onNextButtonClicked

    init {
        _onNextButtonClicked.value = false
    }

    fun onUserNameChange(e: Editable?){
        _userName.value = e?.toString()
    }

    fun onUserSurnameChange(e: Editable?){
        _userSurname.value = e?.toString()
    }

    fun onNavigationCompleted(){
        _onNextButtonClicked.value = false
    }

    fun onNextButtonClicked() {
        _onNextButtonClicked.value = true
        // TODO Keep values to update DB.
    }

}