package com.devotion.makancuy.data.mapper

import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toProduct() =
    Menu(
        name = this?.nama.orEmpty(),
        price = this?.harga ?: 0,
        imageUrl = this?.imageUrl.orEmpty(),
        locationAddress = this?.alamatResto.orEmpty(),
        formattedprice = this?.hargaFormat.orEmpty(),
        details = this?.detail.orEmpty(),
        locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
    )

fun Collection<MenuItemResponse>?.toMenu() = this?.map {
    it.toProduct()
} ?: listOf()
