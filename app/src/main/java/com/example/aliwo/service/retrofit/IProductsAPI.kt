package com.example.aliwo.service.retrofit

import com.example.aliwo.model.ApiProductsModel
import retrofit2.Call
import retrofit2.http.GET

interface IProductsAPI {
    @GET("products")
    fun loadProductData(): Call<List<ApiProductsModel>>
}