package com.devotion.makancuy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String? = UUID.randomUUID().toString(),
    var imageUrl: String,
    var name: String,
    var price: Double,
    var formattedprice: String,
    var details: String,
    var locationAddress: String,
    var locationUrl: String,
) : Parcelable
