package com.devotion.makancuy.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devotion.makancuy.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun doLogin(
        email: String,
        password: String,
    ) = userRepository.doLogin(
        email = email,
        password = password,
    ).asLiveData(Dispatchers.IO)
}
