package com.example.aliwo.model

import com.google.firebase.Timestamp

data class ProductBasketModel(
    var id: Long,
    var title: String,
    var price: Double,
    var image_url: String,
    var category: String,
    var description: String,
    var rate: Float,
    var count: Int,
    var timestamp: Timestamp
)
