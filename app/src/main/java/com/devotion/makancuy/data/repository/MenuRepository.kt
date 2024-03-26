package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.model.Menu

interface MenuRepository {
    fun getMenu(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource): MenuRepository {
    override fun getMenu(): List<Menu> = dataSource.getMenu()
}