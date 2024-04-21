package com.devotion.makancuy.presentation.main

import androidx.lifecycle.ViewModel
import com.devotion.makancuy.data.repository.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel()  {
    fun isLogin() = userRepository.isLoggedIn()
    fun getCurrentUsername() = userRepository.getCurrentUser()?.fullName
}