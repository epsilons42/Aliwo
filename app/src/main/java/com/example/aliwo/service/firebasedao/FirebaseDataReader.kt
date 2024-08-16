package com.example.aliwo.service.firebasedao

import com.example.aliwo.listener.firebaselistener.IBasketProductListener
import com.example.aliwo.listener.firebaselistener.IFavoritesProductListener
import com.example.aliwo.listener.firebaselistener.IUserInfoListener
import com.example.aliwo.model.ProductBasketModel
import com.example.aliwo.model.ProductFavoritesModel
import com.example.aliwo.model.UserInfoModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.math.RoundingMode

class FirebaseDataReader {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseFireStoreDB = FirebaseFirestore.getInstance()

    fun fireStoreUserData(calback: IUserInfoListener) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!
        val userInfoArrayList = ArrayList<UserInfoModel>()
        firebaseFireStoreDB.collection("Users").document(currentUser.uid)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    exception.printStackTrace()
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        val name = snapshot.data?.getOrDefault("name", "").toString()
                        val lastName = snapshot.data?.getOrDefault("lastName", "").toString()
                        val phone = snapshot.data?.getOrDefault("phone", "").toString()
                        userInfoArrayList.add(UserInfoModel(name,lastName,phone))
                        calback.userInfo(userInfoArrayList)
                    }
                }
            }
    }

    fun fireStoreFavoritesData(calbac: IFavoritesProductListener) {
        firebaseAuth = FirebaseAuth.getInstance()
        val uId = firebaseAuth.currentUser!!.uid
        val favoriteProductList = ArrayList<ProductFavoritesModel>()
        firebaseFireStoreDB.collection("Favorites").document(uId).get()
            .addOnSuccessListener { result ->
                if (result != null && result.exists()) {
                    for (doc in result.data!!) {
                        val titlee = doc.value as HashMap<*, *>
                        val id = titlee.get("id").toString().toLong()
                        val title = titlee.get("title").toString()
                        val price = titlee.get("price").toString().toDouble()
                        val image = titlee.get("image").toString()
                        val rate = titlee.get("rate").toString().toFloat()
                        val description = titlee.get("description").toString()
                        val category = titlee.get("category").toString()
                        val timestamp = titlee.get("timestamp") as com.google.firebase.Timestamp

                        val bigDecimal = BigDecimal(price).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        )
                        favoriteProductList.add(
                            ProductFavoritesModel(
                                id,
                                title,
                                bigDecimal.toDouble(),
                                image,
                                category,
                                description,
                                rate,
                                timestamp
                            )
                        )
                    }
                    calbac.productList(favoriteProductList)


                }
            }
            .addOnFailureListener { exception ->
                calbac.productError(exception)
            }
    }

    fun fireStoreBasketData(calback: IBasketProductListener) {
        firebaseAuth = FirebaseAuth.getInstance()
        val uId = firebaseAuth.currentUser!!.uid
        val basketProductList = ArrayList<ProductBasketModel>()
        firebaseFireStoreDB.collection("Basket").document(uId).get()
            .addOnSuccessListener { result ->
                if (result != null && result.exists()) {
                    for (doc in result.data!!) {
                        val productHashMap = doc.value as HashMap<*, *>
                        val id = productHashMap.get("id").toString().toLong()
                        val title = productHashMap.get("title").toString()
                        val price = productHashMap.get("price").toString().toDouble()
                        val image = productHashMap.get("image").toString()
                        val rate = productHashMap.get("rate").toString().toFloat()
                        val description = productHashMap.get("description").toString()
                        val category = productHashMap.get("category").toString()
                        val count = productHashMap.get("count").toString().toInt()
                        val timestamp =
                            productHashMap.get("timestamp") as com.google.firebase.Timestamp
                        val bigDecimal = BigDecimal(price).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        )

                        basketProductList.add(
                            ProductBasketModel(
                                id,
                                title,
                                bigDecimal.toDouble(),
                                image,
                                category,
                                description,
                                rate,
                                count,
                                timestamp
                            )
                        )
                    }
                    calback.productList(basketProductList)
                }
            }
            .addOnFailureListener { exception ->
                calback.productError(exception)
            }
    }
}