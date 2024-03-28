package com.devotion.makancuy.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
    }

    fun getCategory() = categoryRepository.getCategories()
    fun getMenu() = menuRepository.getMenu()
}