package com.example.aliwo.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class IntentUtils {
    fun intentAndClear(context: Context, appCompatActivity: AppCompatActivity) {
        val activityIntent = Intent(context, appCompatActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(activityIntent)
    }

    fun intentActivity(context: Context, appCompatActivity: AppCompatActivity) {
        val intent = Intent(context, appCompatActivity::class.java)
        context.startActivity(intent)
    }

    fun intentActivityAndExtra(context: Context, appCompatActivity: AppCompatActivity, value: Int) {
        val intent = Intent(context, appCompatActivity::class.java)
        intent.putExtra("item", value)
        context.startActivity(intent)
    }

}