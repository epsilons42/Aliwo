package com.example.aliwo.service.firebasedao

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDataUpdater {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseFireStoreDB = FirebaseFirestore.getInstance()
    private fun updateFirestoreUserInfo(
        context: Context,
        currentUserUid: String,
        field: String,
        value: String
    ) {
        firebaseFireStoreDB.collection("Users").document(currentUserUid)
            .update(field, value).addOnSuccessListener {
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    fun updateFireStoreUserData(
        context: Context,
        userName: String,
        userLastName: String,
        userPhone: String
    ) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUserUid = firebaseAuth.currentUser!!.uid
        updateFirestoreUserInfo(context, currentUserUid, "name", userName.replaceFirstChar {
            it.uppercase()
        })

        updateFirestoreUserInfo(context, currentUserUid, "lastName", userLastName.replaceFirstChar {
            it.uppercase()
        })
        updateFirestoreUserInfo(context, currentUserUid, "phone", userPhone)
    }

}