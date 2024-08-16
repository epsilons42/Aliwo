package com.example.aliwo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.service.retrofit.ProductsRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.HashSet

class SearchViewModel : ViewModel() {
    private val searchItemList = HashSet<String>()
    val searchItemMLD = MutableLiveData<HashSet<String>>()

    fun searchItemLoadData() {
        val retrofit = ProductsRetrofitService()
        retrofit.service.loadProductData().enqueue(object : Callback<List<ApiProductsModel>> {
            override fun onResponse(
                call: Call<List<ApiProductsModel>>,
                response: Response<List<ApiProductsModel>>
            ) {
                if (response.isSuccessful) {
                    var index = 0
                    while (index < response.body()!!.size) {
                        searchItemList.add(response.body()!![index].category)
                        index++
                    }
                }
                searchItemMLD.value = searchItemList
            }

            override fun onFailure(call: Call<List<ApiProductsModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}