package com.example.aliwo.viewmodel

import com.example.aliwo.listener.firebaselistener.IUserInfoListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.model.UserInfoModel
import com.example.aliwo.service.firebasedao.FirebaseDataReader
import com.example.aliwo.service.firebasedao.FirebaseUserManager

class UserInfoViewModel : ViewModel() {
    private val firebaseDataReader = FirebaseDataReader()
    private val firebaseUserManager = FirebaseUserManager()
    val userInfoMLD = MutableLiveData<HashMap<String, String>>()

    fun readFireStoreUserData() {
        val userInfoHashMap = HashMap<String, String>()
        val userInfoListener = object : IUserInfoListener {
            override fun userInfo(userInfoArrayList: ArrayList<UserInfoModel>) {
                userInfoHashMap.put("name", userInfoArrayList.get(0).name)
                userInfoHashMap.put("lastName", userInfoArrayList.get(0).lastName)
                userInfoHashMap.put("phone", userInfoArrayList.get(0).phone)
                userInfoHashMap.put("email", firebaseUserManager.currentUserEmail())
                userInfoMLD.value = userInfoHashMap
            }
        }
        firebaseDataReader.fireStoreUserData(userInfoListener)
    }
}