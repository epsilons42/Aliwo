package com.example.aliwo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentSearchRowBinding
import com.example.aliwo.view.fragment.SearchFragmentDirections

class SearchAdapter(private var searchItemList: List<String>) :
    RecyclerView.Adapter<SearchAdapter.SearchVH>() {
    class SearchVH(val dataBinding: FragmentSearchRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentSearchRowBinding>(
            inflater,
            R.layout.fragment_search_row,
            parent,
            false
        )
        return SearchVH(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(searchItemList: List<String>) {
        this.searchItemList = searchItemList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (searchItemList.size > 8) {
            7
        } else {
            searchItemList.size
        }

    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        holder.dataBinding.searchItem = searchItemList[position]
        holder.dataBinding.rowSearchCardView.setOnClickListener {
            val dataDirections =
                SearchFragmentDirections.actionSearchFragmentToProductSearchFragment(searchItemList[position])
            Navigation.findNavController(it)
                .navigate(dataDirections)
        }
    }
}