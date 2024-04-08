package com.devotion.makancuy.data.repository

import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.mapper.toMenu
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.utils.ResultWrapper
import com.devotion.makancuy.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenu(categoryName : String? = null) : Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource): MenuRepository {
    override fun getMenu(categoryName: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenu(categoryName).data.toMenu() }
    }

}