package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentChangePasswordBinding
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick
import com.example.aliwo.service.firebasedao.FirebaseUserManager

class ChangePasswordFragment : Fragment() {
    private lateinit var viewBinding: FragmentChangePasswordBinding
    private val firebaseUserManager = FirebaseUserManager()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        toolbarActions()
        emailVerified()
        viewBinding.fragmentChangePasswordBtnChange.setOnClickListener {
            changePassword()
        }

        return viewBinding.root
    }

    private fun emailVerified() {
        if (firebaseUserManager.firebaseIsEmailVerified()) {
            viewBinding.fragmentChangePasswordCardView.visibility = View.VISIBLE
        } else {
            if (!firebaseUserManager.googleLastSignedInAccount(requireContext())) {
                viewBinding.fragmentChangePasswordTxvWithGoogle.visibility = View.VISIBLE
            }
        }
    }

    private fun changePassword() {
        val newPassword = viewBinding.fragmentChangePasswordEdtNewPassword.text.toString()
        val newPasswordAgain = viewBinding.fragmentChangePasswordEdtNewPasswordAgain.text.toString()
        if (newPassword != "" && newPasswordAgain != "") {
            if (newPassword == newPasswordAgain) {
                firebaseUserManager.changeFirebasePassword(requireContext(), newPassword)
            } else {
                Toast.makeText(context, getString(R.string.password_not_same), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(context, getString(R.string.empty_field), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentChangePasswordToolbar
        toolbar.title = getString(R.string.change_password)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }

}
