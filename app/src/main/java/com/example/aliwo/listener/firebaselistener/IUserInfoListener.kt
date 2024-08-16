package com.example.aliwo.listener.firebaselistener

import com.example.aliwo.model.UserInfoModel

interface IUserInfoListener {
    fun userInfo(userInfoArrayList : ArrayList<UserInfoModel>)
}