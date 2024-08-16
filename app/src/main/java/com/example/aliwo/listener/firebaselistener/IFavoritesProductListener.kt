package com.example.aliwo.listener.firebaselistener

import com.example.aliwo.model.ProductFavoritesModel

interface IFavoritesProductListener {
    fun productList(productArrayList : ArrayList<ProductFavoritesModel>)
    fun productError(exception : Exception)
}