package com.devotion.makancuy.data.datasource.menu
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutRequestPayload
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutResponse
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse
import com.devotion.makancuy.data.source.network.service.RestaurantApiService

class MenuApiDataSource(
    private val service : RestaurantApiService
) : MenuDataSource {
    override suspend fun getMenu(categoryName: String?): MenuResponse {
        return service.getMenu(categoryName)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return  service.createOrder(payload)
    }

}