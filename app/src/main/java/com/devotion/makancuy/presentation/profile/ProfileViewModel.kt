package com.devotion.makancuy.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devotion.makancuy.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getCurrentUser() = userRepository.getCurrentUser()

    fun updateUsername(newName: String) =
        userRepository.updateProfile(
            fullName = newName,
        ).asLiveData(Dispatchers.IO)

    fun updateEmail(newEmail: String) =
        userRepository.updateEmail(
            newEmail = newEmail,
        ).asLiveData(Dispatchers.IO)

    fun doLogout() = userRepository.doLogout().asLiveData(Dispatchers.IO)
}
