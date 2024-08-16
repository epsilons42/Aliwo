package com.example.aliwo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.databinding.FragmentProductParentRowBinding
import com.example.aliwo.model.ProductRVParentModel
import com.example.aliwo.view.fragment.ProductChildFragmentDirections


class ProductParentAdapter(
    val context: Context,
    private val productParentList: List<ProductRVParentModel>
) :
    RecyclerView.Adapter<ProductParentAdapter.ProductParentVH>() {


    class ProductParentVH(val viewBinding: FragmentProductParentRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductParentVH {
        val view = FragmentProductParentRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductParentVH(view)
    }

    override fun getItemCount(): Int {
        return productParentList.size
    }

    override fun onBindViewHolder(holder: ProductParentVH, position: Int) {

        holder.viewBinding.rowProductParentRecyclerView.adapter =
            ProductChildAdapter(context, productParentList.get(position).childList)

        holder.viewBinding.rowProductParentRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        holder.viewBinding.rowProductParentTxvCategory.text = productParentList.get(position).categoryTitle

        //Sends Category and is listed in ProductCategoryAll by category
        holder.viewBinding.rowProductParentBtnAll.setOnClickListener {
            val category = productParentList.get(position).category
            val categoryTitle = productParentList.get(position).categoryTitle
            val navDirections =
                ProductChildFragmentDirections.actionProductChildFragmentToProductCategoryFragment(
                    category,
                    categoryTitle
                )
            Navigation.findNavController(it).navigate(navDirections)
        }
    }
}