package com.devotion.makancuy.data.datasource.menu

import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenu(categoryName : String? = null): MenuResponse
}