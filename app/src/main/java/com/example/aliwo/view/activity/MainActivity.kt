package com.example.aliwo.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.aliwo.R
import com.example.aliwo.databinding.ActivityMainBinding
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.UIUtils

@Suppress("SENSELESS_COMPARISON")
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val firebaseUserManager = FirebaseUserManager()
    private val firebaseDataController = FirebaseDataController()
    private val uIUtil = UIUtils()

    @SuppressLint("DetachAndAttachSameFragment")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activityMainNavHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            viewBinding.activityMainBottomNavigationView,
            navHostFragment.navController
        )

        if (firebaseUserManager.currentUser()) {
            firebaseDataController.checkFavoritesFireStoreDocument()
            firebaseDataController.checkBasketFireStoreDocument()
            navHostFragment.navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
                uIUtil.badgeActivate(this@MainActivity)
            }
        }
    }
}