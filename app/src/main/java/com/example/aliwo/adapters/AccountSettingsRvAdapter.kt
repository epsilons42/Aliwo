package com.example.aliwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.databinding.ProfileButtonRowBinding
import com.example.aliwo.R
import com.example.aliwo.model.ProfileButtonRvModel

class AccountSettingsRvAdapter(private var itemList: List<ProfileButtonRvModel>) :
    RecyclerView.Adapter<AccountSettingsRvAdapter.AccountSettingsVH>() {
    class AccountSettingsVH(val dataBinding: ProfileButtonRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSettingsVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ProfileButtonRowBinding>(
            inflater,
            R.layout.profile_button_row, parent,
            false
        )
        return AccountSettingsVH(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: AccountSettingsVH, position: Int) {
        holder.dataBinding.button = itemList[position]
        holder.dataBinding.profileButtonRowBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(itemList[position].id)
        }
    }
}