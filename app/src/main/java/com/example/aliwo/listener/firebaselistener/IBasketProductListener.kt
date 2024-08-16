package com.example.aliwo.listener.firebaselistener

import com.example.aliwo.model.ProductBasketModel

interface IBasketProductListener {
    fun productList(productArrayList : ArrayList<ProductBasketModel>)
    fun productError(exception : Exception)
}