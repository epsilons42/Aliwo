package com.example.aliwo.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentUserInfoBinding
import com.example.aliwo.viewmodel.UserInfoViewModel
import com.example.aliwo.service.firebasedao.FirebaseDataUpdater
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.emptyControlAndAddText
import com.example.aliwo.util.navigationBackClick

class UserInfoFragment : Fragment() {
    private lateinit var viewBinding: FragmentUserInfoBinding
    private lateinit var userInfoViewModel: UserInfoViewModel
    private val firebaseDataUpdater = FirebaseDataUpdater()
    private var name = String()
    private var lastName = String()
    private var phone = String()
    private var email = String()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserInfoBinding.inflate(inflater, container, false)
        userInfoViewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        userInfoViewModel.readFireStoreUserData()
        toolbarActions()
        viewModelObserve()

        viewBinding.fragmentUserInfoBtnUpdateInfo.setOnClickListener {
            val userName = viewBinding.fragmentUserInfoEdtName.text.toString()
            val userLastName = viewBinding.fragmentUserInfoEdtLastName.text.toString()
            val userPhone = viewBinding.fragmentUserInfoEdtPhone.text.toString()
            firebaseDataUpdater.updateFireStoreUserData(
                requireContext(),
                userName,
                userLastName,
                userPhone
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
                email = it.get("email").toString()
                val nameText = viewBinding.fragmentUserInfoEdtName
                val lastNameText = viewBinding.fragmentUserInfoEdtLastName
                val phoneText = viewBinding.fragmentUserInfoEdtPhone
                viewBinding.fragmentUserInfoTxvEmail.text = email

                nameText.emptyControlAndAddText(name)
                lastNameText.emptyControlAndAddText(lastName)
                phoneText.emptyControlAndAddText(phone)

                textWatcherUserInfo(name, nameText)
                textWatcherUserInfo(lastName, lastNameText)
                textWatcherUserInfo(phone, phoneText)

                nameText.setSelection(nameText.length())
                lastNameText.setSelection(lastNameText.length())
                phoneText.setSelection(phoneText.length())


            }
        }
    }


    private fun textWatcherUserInfo(field: String, editText: EditText) {
        val updateButton = viewBinding.fragmentUserInfoBtnUpdateInfo
        updateButton.isClickable = false
        updateButton.setBackgroundResource(R.color.grey)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (field != editText.text.toString()) {
                    updateButton.setBackgroundResource(R.color.general_application_color)
                    updateButton.isClickable = true
                    if (s!!.length > 0) {
                        editText.setBackgroundResource(R.drawable.edittext_size_shape)
                    } else {
                        editText.setBackgroundResource(R.drawable.edittext_empty_shape)
                    }
                } else {
                    val userName = viewBinding.fragmentUserInfoEdtName.text.toString()
                    val userLastName = viewBinding.fragmentUserInfoEdtLastName.text.toString()
                    val userPhone = viewBinding.fragmentUserInfoEdtPhone.text.toString()
                    editText.setBackgroundResource(R.drawable.edittext_shape)
                    if (name == userName && lastName == userLastName && phone == userPhone) {
                        updateButton.setBackgroundResource(R.color.grey)
                        updateButton.isClickable = false
                    }
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