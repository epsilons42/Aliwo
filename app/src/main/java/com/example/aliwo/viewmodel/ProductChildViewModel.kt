package com.example.aliwo.viewmodel


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.R
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.model.ProductRVChildModel
import com.example.aliwo.model.ProductRVParentModel
import com.example.aliwo.service.retrofit.ProductsRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductChildViewModel : ViewModel() {
    val productParentMLD = MutableLiveData<List<ProductRVParentModel>>()
    val loadMLD = MutableLiveData<Boolean>()
    private val productParentList = ArrayList<ProductRVParentModel>()
    private val productChildList = ArrayList<ProductRVChildModel>()
    private val productChildList2 = ArrayList<ProductRVChildModel>()
    private val productChildList3 = ArrayList<ProductRVChildModel>()
    private val productChildList4 = ArrayList<ProductRVChildModel>()


    fun productListSizeControl(context: Context) {
        if (productParentList.size == 0) {
            productLoadData(context)
        }
    }

    private fun productLoadData(context: Context) {
        val retrofit = ProductsRetrofitService()
        retrofit.service.loadProductData().enqueue(object : Callback<List<ApiProductsModel>> {
            override fun onResponse(
                call: Call<List<ApiProductsModel>>,
                response: Response<List<ApiProductsModel>>
            ) {
                if (response.isSuccessful) {
                    loadMLD.value = true
                    addDataNestedRecyclerView(response)
                    addDataParentList(context)

                }
            }

            override fun onFailure(call: Call<List<ApiProductsModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    //Adds to parentLi
    private fun addDataParentList(context: Context) {
        productParentList.add(
            ProductRVParentModel(
                context.getString(R.string.mansCloting), "men's clothing", productChildList
            )
        )
        productParentList.add(
            ProductRVParentModel(
                context.getString(R.string.womensClothing), "women's clothing", productChildList2
            )
        )
        productParentList.add(
            ProductRVParentModel(
                context.getString(R.string.jewelery), "jewelery", productChildList3
            )
        )
        productParentList.add(
            ProductRVParentModel(
                context.getString(R.string.electronics), "electronics", productChildList4
            )
        )
        productParentMLD.value = productParentList
    }

    private fun whileLoopNestedRV(
        childList: ArrayList<ProductRVChildModel>,
        category: String,
        response: Response<List<ApiProductsModel>>
    ) {
        var indeks = 0
        var element = 0

        while (indeks < response.body()!!.size) {
            if (response.body()!!.get(indeks).category == category) {
                if (element < 4) {
                    childList.add(
                        ProductRVChildModel(
                            response.body()!!.get(indeks).id,
                            response.body()!!.get(indeks).title,
                            response.body()!!.get(indeks).image,
                            response.body()!!.get(indeks).price,
                            response.body()!!.get(indeks).rating.rate,
                            response.body()!!.get(indeks).description,
                            response.body()!!.get(indeks).category
                        )
                    )

                    element++
                }

            }
            indeks++
        }
    }

    //Add to childLists and select category
    private fun addDataNestedRecyclerView(response: Response<List<ApiProductsModel>>) {
        if (productChildList.size == 0) {
            whileLoopNestedRV(
                productChildList,
                "men's clothing", response
            )
            whileLoopNestedRV(
                productChildList2,
                "women's clothing", response
            )
            whileLoopNestedRV(
                productChildList3,
                "jewelery", response
            )
            whileLoopNestedRV(
                productChildList4,
                "electronics", response
            )
        }
    }
}