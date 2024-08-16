package com.example.aliwo.util

import android.content.Context

class PreferencesUtils {
    fun sortRadioButtonRefresh(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences("radioButtonClick", Context.MODE_PRIVATE)
        val sharedPreferencesEditor = sharedPreferences?.edit()
        sharedPreferencesEditor?.clear()
        sharedPreferencesEditor?.apply()
    }
}