package com.example.aliwo.model

import com.example.aliwo.model.ProductRVChildModel

data class ProductRVParentModel(
    val categoryTitle: String,
    val category: String,
    val childList: List<ProductRVChildModel>
)
