package com.example.aliwo.service.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsRetrofitService {
    private val PRODUCTS_BASE_URL = "https://fakestoreapi.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(PRODUCTS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(IProductsAPI::class.java)
}