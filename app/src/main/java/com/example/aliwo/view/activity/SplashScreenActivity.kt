package com.example.aliwo.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.aliwo.R
import com.example.aliwo.util.IntentUtils

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val intentUtils = IntentUtils()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        toMainActivity(1)
    }

    fun toMainActivity(second: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            intentUtils.intentAndClear(this@SplashScreenActivity, MainActivity())
        }, second * 1000)
    }
}
