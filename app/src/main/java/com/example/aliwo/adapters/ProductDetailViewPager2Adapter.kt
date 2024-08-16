package com.example.aliwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.databinding.ProductDetailViewpagerItemBinding
import com.example.aliwo.util.Glide

class ProductDetailViewPager2Adapter(val imageList: List<String>) : RecyclerView.Adapter<ProductDetailViewPager2Adapter.ViewHolderA>() {
    class ViewHolderA(val viewBinding : ProductDetailViewpagerItemBinding) : RecyclerView.ViewHolder(viewBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderA {
        val inflater = LayoutInflater.from(parent.context)
        val view = ProductDetailViewpagerItemBinding.inflate(inflater,parent,false)
        return ViewHolderA(view)
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolderA, position: Int) {

        holder.viewBinding.productDetailViewPagerTemImv.Glide(imageList.get(position))
    }
}