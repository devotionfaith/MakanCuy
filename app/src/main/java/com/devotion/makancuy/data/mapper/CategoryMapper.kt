package com.devotion.makancuy.data.mapper

import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        name = this?.nama.orEmpty(),
        imageurl = this?.imageUrl.orEmpty(),
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map {
        it.toCategory()
    } ?: listOf()
