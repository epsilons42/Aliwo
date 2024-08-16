package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aliwo.R
import com.example.aliwo.adapters.AccountSettingsRvAdapter
import com.example.aliwo.databinding.FragmentAccountSettingsBinding
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick
import com.example.viewmodel.AccountSettingsViewModel

class AccountSettingsFragment : Fragment() {
    private lateinit var viewBinding: FragmentAccountSettingsBinding
    private lateinit var accountSettingsViewModel: AccountSettingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAccountSettingsBinding.inflate(inflater, container, false)
        accountSettingsViewModel = ViewModelProvider(this).get(AccountSettingsViewModel::class.java)
        accountSettingsViewModel.controlAddProfileList(requireContext())
        viewModelObserve()
        toolbarActions()
        return viewBinding.root
    }

    private fun viewModelObserve() {
        accountSettingsViewModel.accountSettingsItemMLD.observe(viewLifecycleOwner) { account ->
            account?.let {
                viewBinding.fragmentAccountSettingsRv.adapter = AccountSettingsRvAdapter(it)
                viewBinding.fragmentAccountSettingsRv.layoutManager = LinearLayoutManager(context)
            }

        }
    }

    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentAccountSettingsToolbar
        toolbar.title = getString(R.string.account_settings)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }
}