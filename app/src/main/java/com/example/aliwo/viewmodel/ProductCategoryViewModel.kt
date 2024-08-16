package com.example.aliwo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.service.retrofit.ProductsRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductCategoryViewModel : ViewModel() {
     val productArrayList = ArrayList<ApiProductsModel>()
    val productMLD = MutableLiveData<ArrayList<ApiProductsModel>>()
    val productLoadMLD = MutableLiveData<Boolean>()

    fun productListSizeControl(category: String){
        if(productArrayList.size == 0){
            productLoadData(category)
        }
    }

    private fun productLoadData(category: String) {
        val retrofit = ProductsRetrofitService()
        retrofit.service.loadProductData().enqueue(object : Callback<List<ApiProductsModel>> {
            override fun onResponse(
                call: Call<List<ApiProductsModel>>,
                response: Response<List<ApiProductsModel>>
            ) {

                if (response.isSuccessful) {
                    val productList = response.body()!!.filter { p ->
                        p.category.equals(category)
                    }.toList()
                    productArrayList.addAll(productList)
                    productMLD.value = productArrayList
                    productLoadMLD.value = true
                }
            }

            override fun onFailure(call: Call<List<ApiProductsModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}