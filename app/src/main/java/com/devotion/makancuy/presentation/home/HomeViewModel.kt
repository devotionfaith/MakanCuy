package com.devotion.makancuy.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.source.local.pref.UserPreference

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreference: UserPreference
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(userPreference.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

    fun getCategory() = categoryRepository.getCategories()
    fun getMenu() = menuRepository.getMenu()
}