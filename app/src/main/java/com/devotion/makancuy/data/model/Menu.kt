package com.devotion.makancuy.data.model

import java.util.UUID

data class Menu (
    var id: String = UUID.randomUUID().toString(),
    var imageurl: String,
    var name: String,
    var price: Double,
    var details: String,
    var locationAddress: String,
    var locationUrl: String
)