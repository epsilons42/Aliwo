package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentEmailVerificationBinding
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth

class EmailVerificationFragment : Fragment() {
    private lateinit var viewBinding: FragmentEmailVerificationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseUserManager = FirebaseUserManager()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEmailVerificationBinding.inflate(inflater, container, false)
        toolbarActions()

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!

        if (currentUser.isEmailVerified) {
            viewBinding.fragmentEmailVerificationTxvCurrentEmail
                .setCompoundDrawablesWithIntrinsicBounds(R.drawable.asset_tic, 0, 0, 0)
        }
        val currentTextView = viewBinding.fragmentEmailVerificationTxvCurrentEmail

        currentTextView.text = firebaseAuth.currentUser!!.email

        viewBinding.fragmentEmailVerificationBtnEmailVerification.setOnClickListener {
            firebaseUserManager.firebaseEmailVerification(requireContext())
        }
        return viewBinding.root

    }


    override fun onResume() {
        super.onResume()
        val currentTextView = viewBinding.fragmentEmailVerificationTxvCurrentEmail
        firebaseUserManager.refreshFirebaseEmailVerification(currentTextView)
    }

    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentEmailVerificationToolbar
        toolbar.title = getString(R.string.email_verification)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }
}