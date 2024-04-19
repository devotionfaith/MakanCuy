package com.devotion.makancuy.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devotion.makancuy.data.model.Profile
import com.devotion.makancuy.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val userRepository: UserRepository)  : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            name = "Rizky Gustiantoro",
            username = "rgustiann",
            email = "rgusti530@gmail.com",
            profileImg = "https://instagram.fjog1-1.fna.fbcdn.net/v/t51.2885-19/364080408_829727922079943_5389332852020861258_n.jpg?stp=dst-jpg_s320x320&_nc_ht=instagram.fjog1-1.fna.fbcdn.net&_nc_cat=111&_nc_ohc=bnvlIBQYzyAAX8dLosF&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AfBoouIT5iNjJRQoVvC92otlS1kTXbk08kr0U6W2528WeQ&oe=660B8BC4&_nc_sid=8b3546"
        )
    )

    fun doLogout() = userRepository.doLogout().asLiveData(Dispatchers.IO)
    val isEditMode = MutableLiveData(false)
    val buttonText = MutableLiveData("Edit")

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun getButtonText() {
        val currentValue = isEditMode.value ?: false
        return if (!currentValue) {
            buttonText.postValue("Edit")
        } else {
            buttonText.postValue("Edit")
        }
    }
}