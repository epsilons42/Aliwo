package com.example.aliwo.service.firebasedao

import com.example.aliwo.listener.firebaselistener.IListExistListener
import com.example.aliwo.model.ProductIdModel
import com.example.aliwo.listener.firebaselistener.IBasketListSizeListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDataController {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseFireStoreDB = FirebaseFirestore.getInstance()

    private fun createFireStoreDataFavorites() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        val favoritesHashMap = HashMap<String, Map<String, Any>>()
        firebaseFireStoreDB.collection("Favorites").document(currentUserUid).set(favoritesHashMap)
            .addOnSuccessListener {
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    private fun createFireStoreDataBasket() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        val basketHashMap = HashMap<String, ArrayList<ProductIdModel>>()
        val productIdList = ArrayList<ProductIdModel>()
        firebaseFireStoreDB.collection("Basket").document(currentUserUid).set(basketHashMap)
            .addOnSuccessListener {
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun checkFavoritesFireStoreDocument() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Favorites").document(currentUserUid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        if (!document.exists()) {
                            createFireStoreDataFavorites()
                        }
                    }
                }
            }.addOnFailureListener { exception ->
                exception.localizedMessage
            }
    }

    fun checkBasketFireStoreDocument() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Basket").document(currentUserUid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        if (!document.exists()) {
                            createFireStoreDataBasket()
                        }
                    }
                }
            }.addOnFailureListener { exception ->
                exception.localizedMessage
            }
    }

    fun controlFireStoreDataFavorites(productId: Long, calback: IListExistListener) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Favorites").document(currentUserUid).get()
            .addOnSuccessListener { result ->
                if (result != null && result.exists()) {
                    val product = result[productId.toString()]
                    if (product.toString().contains(productId.toString())) {
                        calback.controlFavoritesList(true)
                    } else {
                        calback.controlFavoritesList(false)
                    }
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun controlFireStoreDataBasket(productId: Long, calback: IListExistListener) {

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Basket").document(currentUserUid).get()
            .addOnSuccessListener { result ->
                if (result != null && result.exists()) {
                    val product = result[productId.toString()]
                    if (product.toString().contains(productId.toString())) {
                        calback.controlBasketList(true)
                    } else {
                        calback.controlBasketList(false)
                    }
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun controlBasketDataSize(calback: IBasketListSizeListener) {
        firebaseAuth = FirebaseAuth.getInstance()
        val uId = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Basket").document(uId).get()
            .addOnSuccessListener { result ->
                if (result != null && result.exists()) {
                    val productHashNapSize = result.data?.size
                    if (productHashNapSize != null) {
                        calback.controlBasketListSize(productHashNapSize)
                    } else {
                        calback.controlBasketListSize(0)
                    }
                }
            }

    }

}