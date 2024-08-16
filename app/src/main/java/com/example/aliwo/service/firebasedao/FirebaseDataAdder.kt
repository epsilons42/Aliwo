package com.example.aliwo.service.firebasedao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirebaseDataAdder {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseFireStoreDB = FirebaseFirestore.getInstance()

    fun createFireStoreUserDataProfile() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        val usersHashMap = HashMap<String, Any>()
        usersHashMap.put("name", "")
        usersHashMap.put("lastName", "")
        usersHashMap.put("phone", "")
        firebaseFireStoreDB.collection("Users").document(currentUserUid).set(usersHashMap)
            .addOnCompleteListener { task ->
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun addFireStoreDataFavorites(currentProductId: Long, productMap: Map<String, Any>) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        val favoritesHashMap = HashMap<String, Map<String, Any>>()
        favoritesHashMap.put(currentProductId.toString(), productMap)
        firebaseFireStoreDB.collection("Favorites").document(currentUserUid).set(
            favoritesHashMap,
            SetOptions.merge()
        )
            .addOnSuccessListener {
            }
    }

    fun addFireStoreDataBasket(currentProductId: Long, productMap: Map<String, Any>) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        val basketHashMap = HashMap<String, Map<String, Any>>()
        basketHashMap.put(currentProductId.toString(), productMap)
        firebaseFireStoreDB.collection("Basket").document(currentUserUid).set(
            basketHashMap,
            SetOptions.merge()
        )
            .addOnSuccessListener {
            }
    }

}