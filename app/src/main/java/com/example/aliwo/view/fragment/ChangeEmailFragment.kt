package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentChangeEmailBinding
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick
import com.example.aliwo.service.firebasedao.FirebaseUserManager

class ChangeEmailFragment : Fragment() {
    private lateinit var viewBinding: FragmentChangeEmailBinding
    private val firebaseUserManager = FirebaseUserManager()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentChangeEmailBinding.inflate(inflater, container, false)
        toolbarActions()
        changeEmail()
        return viewBinding.root
    }

    private fun changeEmail() {
        if (firebaseUserManager.firebaseIsEmailVerified()) {
            viewBinding.fragmentChangeEmailCardView.visibility = View.VISIBLE
            viewBinding.fragmentChangeEmailTxvCurrentEmail.text =
                firebaseUserManager.firebaseCurrentUserEmail()
        }
        viewBinding.fragmentChangeEmailBtnChange.setOnClickListener {
            val newEmail = viewBinding.fragmentChangeEmailEdtNewEmail.text.toString()
            val newEmailAgain = viewBinding.fragmentChangeEmailEdtNewEmailAgain.text.toString()
            firebaseUserManager.changeFirebaseEmail(requireContext(), newEmail, newEmailAgain)
        }
    }

    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentChangeEmailToolbar
        toolbar.title = getString(R.string.change_email)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }
}