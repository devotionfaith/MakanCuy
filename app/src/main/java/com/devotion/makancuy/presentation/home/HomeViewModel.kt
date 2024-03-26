package com.devotion.makancuy.presentation.home

import androidx.lifecycle.ViewModel
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun getCategory() = categoryRepository.getCategories()
    fun getMenu() = menuRepository.getMenu()
}