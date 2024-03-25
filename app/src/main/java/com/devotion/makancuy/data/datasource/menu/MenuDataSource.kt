package com.devotion.makancuy.data.datasource.menu

import com.devotion.makancuy.data.model.Menu

interface MenuDataSource {
    fun getMenu(): List<Menu>
}