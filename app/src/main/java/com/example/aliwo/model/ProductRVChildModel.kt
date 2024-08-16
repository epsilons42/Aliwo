package com.example.aliwo.model

data class ProductRVChildModel(
    val productId: Long,
    val productName: String,
    val productImage: String,
    val productPrice: Double,
    val productRating: Float,
    val description: String,
    val category: String
)
