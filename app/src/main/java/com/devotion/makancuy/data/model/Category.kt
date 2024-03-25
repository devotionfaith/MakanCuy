package com.devotion.makancuy.data.model

import java.util.UUID

data class Category (
    var id: String = UUID.randomUUID().toString(),
    var image: String,
    var name: String
)