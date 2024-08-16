package com.example.aliwo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.aliwo.R
import com.example.aliwo.adapters.ViewPager2Adapter
import com.example.aliwo.databinding.ActivityLoginSignUpBinding
import com.example.aliwo.view.fragment.LoginFragment
import com.example.aliwo.view.fragment.SignUpFragment
import com.google.android.material.tabs.TabLayoutMediator

class LoginSignUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityLoginSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginSignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(LoginFragment())
        fragmentList.add(SignUpFragment())
        val titleList = ArrayList<String>()
        titleList.add(getString(R.string.login))
        titleList.add(getString(R.string.sing_up))
        val viewPager = viewBinding.activityLoginSignUpVp
        val tableLayout = viewBinding.activityLoginSignUpTl

        viewPager.adapter = ViewPager2Adapter(this, fragmentList)
        TabLayoutMediator(tableLayout, viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()

        val getIntExtra = intent.getIntExtra("item", 0)
        viewPager.setCurrentItem(getIntExtra, false)
    }
}

