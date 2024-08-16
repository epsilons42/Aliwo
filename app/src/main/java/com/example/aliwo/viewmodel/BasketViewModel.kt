package com.example.aliwo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.listener.firebaselistener.IBasketProductListener
import com.example.aliwo.model.ProductBasketModel
import com.example.aliwo.service.firebasedao.FirebaseDataReader
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth

class BasketViewModel : ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuth
    val productMLD = MutableLiveData<ArrayList<ProductBasketModel>>()
    val firebaseDataReader = FirebaseDataReader()
    val firebaseUserManager = FirebaseUserManager()

    fun controlCurrentUser() {
        if(firebaseUserManager.currentUser()) {
            loadBasketProduct()
        }
    }

    private fun loadBasketProduct() {
        firebaseAuth = FirebaseAuth.getInstance()
        val uId = firebaseAuth.currentUser!!.uid

        val basketProductListener = object : IBasketProductListener {
            override fun productList(productList: ArrayList<ProductBasketModel>) {

                productList.sortedBy {
                    it.timestamp
                }
                productList.reverse()
                productMLD.value = productList
            }

            override fun productError(error: Exception) {
                error.printStackTrace()
            }

        }
        firebaseDataReader.fireStoreBasketData(basketProductListener)
    }
}
