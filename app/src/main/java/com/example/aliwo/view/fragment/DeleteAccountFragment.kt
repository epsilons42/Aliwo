package com.example.aliwo.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentDeleteAccountBinding
import com.example.aliwo.util.DialogUtils
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick

class DeleteAccountFragment : Fragment() {
    private lateinit var viewBinding: FragmentDeleteAccountBinding
    private val dialogUtils = DialogUtils()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        toolbarActions()
        viewBinding.fragmentDeleteAccountBtnDelete.setOnClickListener {
            val userEmail = viewBinding.fragmentDeleteAccountEdtEmail.text.toString()
            dialogUtils.deleteAccoundAlerdDialog(requireContext(),userEmail)
        }
        return viewBinding.root
    }
    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentDeleteAccountToolbar
        toolbar.title = getString(R.string.delete_account)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }
}
