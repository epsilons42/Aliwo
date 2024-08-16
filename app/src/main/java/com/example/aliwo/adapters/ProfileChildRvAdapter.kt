package com.example.aliwo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.R
import com.example.aliwo.databinding.ProfileButtonRowBinding
import com.example.aliwo.model.ProfileButtonRvModel
import com.example.aliwo.service.firebasedao.FirebaseUserManager

class ProfileChildRvAdapter(
    val context: Context,
    private val profileButtonList: List<ProfileButtonRvModel>
) :
    RecyclerView.Adapter<ProfileChildRvAdapter.ProfileChildVH>() {
    val firebaseUserManager = FirebaseUserManager()

    class ProfileChildVH(val dataBinding: ProfileButtonRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileChildVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ProfileButtonRowBinding>(
            inflater,
            R.layout.profile_button_row, parent,
            false
        )
        return ProfileChildVH(view)
    }

    override fun getItemCount(): Int {
        return profileButtonList.size
    }

    override fun onBindViewHolder(holder: ProfileChildVH, position: Int) {
        holder.dataBinding.button = profileButtonList.get(position)
        holder.dataBinding.profileButtonRowBtn.setOnClickListener {
            if (profileButtonList.get(position).id == R.id.profileButtonRowBtn) {
                firebaseUserManager.logOut(context)
            } else {
                Navigation.findNavController(it).navigate(profileButtonList.get(position).id)
            }
        }
    }
}