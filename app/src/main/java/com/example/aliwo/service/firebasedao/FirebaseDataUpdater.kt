package com.example.aliwo.service.firebasedao

import android.content.Context
import com.example.aliwo.listener.firebaselistener.IUpdateListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDataUpdater {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseFireStoreDB = FirebaseFirestore.getInstance()
    private fun updateFirestoreUserInfo(
        context: Context,
        currentUserUid: String,
        field: String,
        value: String,
        calback: IUpdateListener
    ) {
        firebaseFireStoreDB.collection("Users").document(currentUserUid)
            .update(field, value).addOnSuccessListener {
                calback.userInfoUpdateListener(true)
            }.addOnFailureListener { exception ->
                calback.userInfoUpdateListener(false)
            }
    }

    fun updateFireStoreUserData(
        context: Context,
        userName: String,
        userLastName: String,
        userPhone: String,
        calback: IUpdateListener
    ) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        updateFirestoreUserInfo(context, currentUserUid, "name", userName.replaceFirstChar {
            it.uppercase()
        },calback)

        updateFirestoreUserInfo(context, currentUserUid, "lastName", userLastName.replaceFirstChar {
            it.uppercase()
        },calback)
        updateFirestoreUserInfo(context, currentUserUid, "phone", userPhone,calback)
    }

    fun updateFireStoreBasketCount(currentProductId: Long, count: Int,calback : IUpdateListener) {
        firebaseAuth = FirebaseAuth.getInstance()
        val map = mapOf(
            "$currentProductId.count" to count
        )
        val basketHashMap = HashMap<String, Map<String, Any>>()
        basketHashMap.put(currentProductId.toString(), map)
        val currentUserUid = firebaseAuth.currentUser!!.uid
        firebaseFireStoreDB.collection("Basket").document(currentUserUid)
            .update(
                map
            ).addOnSuccessListener {
                calback.userInfoUpdateListener(true)
            }.addOnFailureListener {
                calback.userInfoUpdateListener(false)
            }
    }
}