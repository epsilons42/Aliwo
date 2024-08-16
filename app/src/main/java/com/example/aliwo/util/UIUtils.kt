package com.example.aliwo.util

import androidx.fragment.app.FragmentActivity
import com.example.aliwo.R
import com.example.aliwo.listener.firebaselistener.IBasketListSizeListener
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class UIUtils {
    private fun basketBadge(requireActivity: FragmentActivity, size: Int) {
        val bottomNavView =
            requireActivity.findViewById(R.id.activityMainBottomNavigationView) as BottomNavigationView
        val badge: BadgeDrawable = bottomNavView.getOrCreateBadge(R.id.basketFragment)
        badge.number = size
        badge.backgroundColor = requireActivity.getColor(R.color.general_application_color)
        badge.isVisible = true
        if (size == 0) {
            badge.isVisible = false
        }
    }

    fun badgeActivate(requireActivity: FragmentActivity) {
        val firebaseDataController = FirebaseDataController()
        val basketListSizeListener = object : IBasketListSizeListener {
            override fun controlBasketListSize(listSize: Int) {
                basketBadge(requireActivity, listSize)
            }
        }
        firebaseDataController.controlBasketDataSize(basketListSizeListener)
    }
}