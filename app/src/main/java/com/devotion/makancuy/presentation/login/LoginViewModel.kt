package com.devotion.makancuy.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
//    private val _loginResult = MutableLiveData<ResultWrapper<Boolean>>()
//    val loginResult: LiveData<ResultWrapper<Boolean>>
//        get() = _loginResult

    fun doLogin(email: String, password: String) = userRepository.doLogin(
        email = email,
        password = password
    ).asLiveData(Dispatchers.IO)
}