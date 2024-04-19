package com.devotion.makancuy.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun doRegister(fullName: String, email: String, password: String) =
        userRepository.doRegister(
            fullName = fullName,
            email = email,
            password = password
        ).asLiveData(Dispatchers.IO)
}