package com.example.aliwo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.service.firebasedao.FirebaseDataReader
import com.example.aliwo.listener.firebaselistener.IFavoritesProductListener
import com.example.aliwo.model.ProductFavoritesModel
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth

class FavoritesViewModel : ViewModel() {
    private lateinit var firebaseAuth: FirebaseAuth
    val firebaseDataReader = FirebaseDataReader()
    val productMLD = MutableLiveData<ArrayList<ProductFavoritesModel>>()
    val firebaseUserManager = FirebaseUserManager()

    fun controlCurrentUser() {
        if(firebaseUserManager.currentUser()) {
            loadFavoritesProduct()
        }

    }
    private fun loadFavoritesProduct() {

        val favoritesProductListener = object : IFavoritesProductListener {
            override fun productList(productArrayList: ArrayList<ProductFavoritesModel>) {
                productArrayList.sortedBy {
                    it.timestamp
                }
                productArrayList.reverse()
                productMLD.value = productArrayList
            }

            override fun productError(exception: Exception) {
                exception.printStackTrace()
            }

        }
        firebaseDataReader.fireStoreFavoritesData(favoritesProductListener)

    }
}
