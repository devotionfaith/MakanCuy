package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.mapper.toMenus
import com.devotion.makancuy.data.model.Cart
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutItemPayload
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutRequestPayload
import com.devotion.makancuy.utils.ResultWrapper
import com.devotion.makancuy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenu(categoryName: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(
        menu: List<Cart>,
        username: String,
    ): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenu(categoryName: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenu(categoryName).data.toMenus() }
    }

    override fun createOrder(
        menu: List<Cart>,
        username: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    username = username,
                    total = menu.sumOf { it.menuPrice * it.itemQuantity },
                    orders =
                        menu.map {
                            CheckoutItemPayload(
                                notes = it.itemNotes,
                                name = it.menuName,
                                qty = it.itemQuantity,
                                price = it.menuPrice,
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
