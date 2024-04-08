package com.devotion.makancuy.data.datasource.menu
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse
import com.devotion.makancuy.data.source.network.service.RestaurantApiService

class MenuApiDataSource(
    private val service : RestaurantApiService
) : MenuDataSource {
    override suspend fun getMenu(categoryName: String?): MenuResponse {
        return service.getMenu(categoryName)
    }

}