package com.example.aliwo.view.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentUserInfoBinding
import com.example.aliwo.listener.firebaselistener.IUpdateListener
import com.example.aliwo.viewmodel.UserInfoViewModel
import com.example.aliwo.service.firebasedao.FirebaseDataUpdater
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick

class UserInfoFragment : Fragment() {
    private lateinit var viewBinding: FragmentUserInfoBinding
    private lateinit var userInfoViewModel: UserInfoViewModel
    private val firebaseDataUpdater = FirebaseDataUpdater()
    private val firebaseUserManager = FirebaseUserManager()
    private var name = ""
    private var lastName = ""
    private var phone = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserInfoBinding.inflate(inflater, container, false)
        userInfoViewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        userInfoViewModel.readFireStoreUserData()
        toolbarActions()
        viewModelObserve()

        viewBinding.fragmentUserInfoTxvEmail.text = firebaseUserManager.currentUserEmail()

        viewBinding.fragmentUserInfoBtnUpdateInfo.setOnClickListener {
            val userName = viewBinding.fragmentUserInfoEdtName.text.toString()
            val userLastName = viewBinding.fragmentUserInfoEdtLastName.text.toString()
            val userPhone = viewBinding.fragmentUserInfoEdtPhone.text.toString()

            visibilityControl(true)
            val updateListener = object : IUpdateListener {
                override fun userInfoUpdateListener(update: Boolean) {
                    if (update) {
                        refreshFragment()
                    } else {
                        visibilityControl(false)
                        Toast.makeText(context, getString(R.string.sorry_wrong), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
            firebaseDataUpdater.updateFireStoreUserData(
                requireContext(),
                userName.trim(),
                userLastName.trim(),
                userPhone.trim(), updateListener
            )
        }
        return viewBinding.root
    }

    private fun viewModelObserve() {
        userInfoViewModel.userInfoMLD.observe(viewLifecycleOwner) { user ->
            user?.let {
                name = it.get("name").toString()
                lastName = it.get("lastName").toString()
                phone = it.get("phone").toString()
                editTextActive()

            }
        }
    }

    private fun refreshFragment() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        Handler(Looper.getMainLooper()).postDelayed({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentTransaction.detach(this).commitNow()
                fragmentTransaction.attach(this).commitNow()
            } else {
                fragmentTransaction.detach(this).attach(this).commit()
            }
        }, 500)

    }

    private fun visibilityControl(visible: Boolean) {
        val cardView = viewBinding.fragmentUserInfoCv
        val progressBar = viewBinding.fragmentUserInfoPgb
        if (visible) {
            cardView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }else{
            cardView.visibility = View.VISIBLE
            progressBar.visibility =View.GONE
        }
    }


    private fun editTextActive() {
        val nameText = viewBinding.fragmentUserInfoEdtName
        val lastNameText = viewBinding.fragmentUserInfoEdtLastName
        val phoneText = viewBinding.fragmentUserInfoEdtPhone

        textWatcherUserInfo(name, nameText)
        textWatcherUserInfo(lastName, lastNameText)
        textWatcherUserInfo(phone, phoneText)

        viewBinding.fragmentUserInfoEdtName.setText(name)
        viewBinding.fragmentUserInfoEdtLastName.setText(lastName)
        viewBinding.fragmentUserInfoEdtPhone.setText(phone)


        nameText.setSelection(nameText.length())
        lastNameText.setSelection(lastNameText.length())
        phoneText.setSelection(phoneText.length())
    }


    private fun textWatcherUserInfo(field: String, editText: EditText) {
        val updateButton = viewBinding.fragmentUserInfoBtnUpdateInfo
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (field != editText.text.toString()) {
                    updateButton.setBackgroundResource(R.color.general_application_color)
                    updateButton.isClickable = true

                } else {
                    updateButton.setBackgroundResource(R.color.grey)
                    updateButton.isClickable = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentUserInfoToolbar
        toolbar.title = getString(R.string.user_information)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }
}