package com.devotion.makancuy.data.datasource.menu

import com.devotion.makancuy.data.source.network.model.checkout.CheckoutRequestPayload
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutResponse
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenu(categoryName: String? = null): MenuResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
