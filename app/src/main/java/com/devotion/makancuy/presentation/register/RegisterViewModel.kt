package com.devotion.makancuy.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devotion.makancuy.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ) = userRepository.doRegister(
        fullName = fullName,
        email = email,
        password = password,
    ).asLiveData(Dispatchers.IO)
}
