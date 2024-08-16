package com.example.aliwo.service.firebasedao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDataDeleter {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseFireStoreDB = FirebaseFirestore.getInstance()

    fun deleteAllFireStoreCollection() {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Users").document(currentUserUid).delete()
        firebaseFireStoreDB.collection("Favorites").document(currentUserUid).delete()
        firebaseFireStoreDB.collection("Basket").document(currentUserUid).delete()
    }

    fun deleteFireStoreDataFavorites(currentProductId: Long) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Favorites").document(currentUserUid)
            .update(currentProductId.toString(), FieldValue.delete()).addOnFailureListener {

            }
    }

    fun deleteFireStoreDataBasket(currentProductId: Long) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Basket").document(currentUserUid)
            .update(currentProductId.toString(), FieldValue.delete()).addOnFailureListener {

            }
    }
}