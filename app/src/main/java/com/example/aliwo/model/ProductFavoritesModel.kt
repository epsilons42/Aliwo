package com.example.aliwo.model

import com.google.firebase.Timestamp

data class ProductFavoritesModel(
    val id: Long,
    val title: String,
    val price: Double,
    val image_url: String,
    val category : String,
    val description : String,
    val rate: Float,
    val timestamp : Timestamp
)
