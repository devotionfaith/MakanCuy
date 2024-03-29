package com.devotion.makancuy.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devotion.makancuy.data.model.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            name = "Rizky Gustiantoro",
            username = "rgustiann",
            email = "rgusti530@gmail.com",
            profileImg = "https://instagram.fjog1-1.fna.fbcdn.net/v/t51.2885-19/364080408_829727922079943_5389332852020861258_n.jpg?stp=dst-jpg_s320x320&_nc_ht=instagram.fjog1-1.fna.fbcdn.net&_nc_cat=111&_nc_ohc=bnvlIBQYzyAAX8dLosF&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AfBoouIT5iNjJRQoVvC92otlS1kTXbk08kr0U6W2528WeQ&oe=660B8BC4&_nc_sid=8b3546"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}