package com.devotion.makancuy.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "product_id")
    var productId: String? = null,
    @ColumnInfo(name = "product_name")
    var productName: String? = null,
    @ColumnInfo(name = "product_img_url")
    var productImgUrl: String? = null,
    @ColumnInfo(name = "product_price")
    var productPrice: String? = null,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity: String? = null,
    @ColumnInfo(name = "item_notes")
    var itemNotes: String? = null
    )
