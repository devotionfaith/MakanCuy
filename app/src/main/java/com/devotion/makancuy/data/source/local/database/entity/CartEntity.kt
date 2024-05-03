package com.devotion.makancuy.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "menu_id")
    var menuId: String? = null,
    @ColumnInfo(name = "menu_name")
    var menuName: String? = null,
    @ColumnInfo(name = "menu_img_url")
    var menuImgUrl: String? = null,
    @ColumnInfo(name = "menu_price")
    var menuPrice: Double,
    @ColumnInfo(name = "menu_price_formatted")
    var menuPriceFormatted: String? = null,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int? = null,
    @ColumnInfo(name = "item_notes")
    var itemNotes: String? = null,
)
