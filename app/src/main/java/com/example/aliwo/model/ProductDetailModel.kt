package com.example.aliwo.model

data class ProductDetailModel(
    val id: Long,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rate: Float
)
