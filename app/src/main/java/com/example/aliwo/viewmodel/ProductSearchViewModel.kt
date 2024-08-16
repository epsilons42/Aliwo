package com.example.aliwo.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.service.retrofit.IProductsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductSearchViewModel : ViewModel() {
    val productMLD = MutableLiveData<List<ApiProductsModel>>()
    val productLoadMLD = MutableLiveData<Boolean>()

    fun productLoadData(category: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(IProductsAPI::class.java)

        retrofit.loadProductData().enqueue(object : Callback<List<ApiProductsModel>> {
            override fun onResponse(
                call: Call<List<ApiProductsModel>>,
                response: Response<List<ApiProductsModel>>
            ) {
                if (response.isSuccessful) {
                    val productList = response.body()!!.filter { p ->
                        p.category.equals(category)
                    }.toList()

                    productMLD.value = productList
                    productLoadMLD.value = true
                }

            }
            override fun onFailure(call: Call<List<ApiProductsModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}