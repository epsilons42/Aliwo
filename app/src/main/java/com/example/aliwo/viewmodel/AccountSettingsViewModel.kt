package com.example.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.R
import com.example.aliwo.model.ProfileButtonRvModel
import com.example.aliwo.service.firebasedao.FirebaseUserManager

class AccountSettingsViewModel : ViewModel() {
    private val accountSettingsItemArrayList = ArrayList<ProfileButtonRvModel>()
    private val firebaseUserManager = FirebaseUserManager()
    val accountSettingsItemMLD = MutableLiveData<List<ProfileButtonRvModel>>()
    fun controlAddProfileList(context: Context) {
        if (accountSettingsItemArrayList.size == 0) {
            addAccountSettingsItem(context)
        }
    }
    private fun addAccountSettingsItem(context: Context) {
        if(firebaseUserManager.firebaseIsEmailVerified()){
            accountSettingsItemArrayList.add(
                ProfileButtonRvModel(
                    R.id.action_accountSettingsFragment_to_changeEmailFragment,
                    context.getString(R.string.change_email),
                    R.drawable.asset_email
                )
            )
            accountSettingsItemArrayList.add(
                ProfileButtonRvModel(
                    R.id.action_accountSettingsFragment_to_changePasswordFragment,
                    context.getString(R.string.change_password),
                    R.drawable.asset_key
                )
            )
        }else{
            accountSettingsItemArrayList.add(
                ProfileButtonRvModel(
                    R.id.action_accountSettingsFragment_to_emailVerificationFragment,
                    context.getString(R.string.change_email),
                    R.drawable.asset_email
                )
            )
            accountSettingsItemArrayList.add(
                ProfileButtonRvModel(
                    R.id.action_accountSettingsFragment_to_emailVerificationFragment,
                    context.getString(R.string.change_password),
                    R.drawable.asset_key
                )
            )
        }

        accountSettingsItemArrayList.add(
            ProfileButtonRvModel(
                R.id.action_accountSettingsFragment_to_emailVerificationFragment,
                context.getString(R.string.email_verification),
                R.drawable.asset_email_verification
            )
        )
        accountSettingsItemArrayList.add(
            ProfileButtonRvModel(
                R.id.action_accountSettingsFragment_to_disableDeleteAccountFragment,
                context.getString(R.string.delete_account),
                R.drawable.asset_delete
            )
        )
        accountSettingsItemMLD.value = accountSettingsItemArrayList
    }

}