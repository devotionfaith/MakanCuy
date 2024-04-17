package com.devotion.makancuy.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutRequestPayload(
    @SerializedName("username")
    val username : String,
    @SerializedName("total")
    val total : Int,
    @SerializedName("orders")
    val orders : List<CheckoutItemPayload>
)