package com.example.aliwo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentBasketRowBinding
import com.example.aliwo.model.ProductBasketModel
import com.example.aliwo.view.fragment.BasketFragmentDirections
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.util.UIUtils


class BasketAdapter(
    val context: Context,
    private val basketArray: ArrayList<ProductBasketModel>,
    private val fragmentActivity: FragmentActivity
) :
    RecyclerView.Adapter<BasketAdapter.BasketVH>() {
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val uIUtil = UIUtils()

    class BasketVH(val dataBinding: FragmentBasketRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentBasketRowBinding>(
            inflater,
            R.layout.fragment_basket_row,
            parent,
            false
        )
        return BasketVH(view)
    }

    override fun getItemCount(): Int {
        return basketArray.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BasketVH, position: Int) {
        holder.dataBinding.product = basketArray[position]
        holder.dataBinding.recyclerRowBasketBtnDelete.setOnClickListener {
            uIUtil.badgeActivate(fragmentActivity)
            firebaseDataDeleter.deleteFireStoreDataBasket(basketArray.get(position).id)
            basketArray.removeAt(position)
            notifyDataSetChanged()

        }


        holder.dataBinding.recyclerRowBasketCardViewProduct.setOnClickListener {
            val navArgs =
                BasketFragmentDirections.actionBasketFragmentToProductDetailFragment(
                    basketArray.get(position).id,
                    basketArray.get(position).title,
                    basketArray.get(position).price.toFloat(),
                    basketArray.get(position).description,
                    basketArray.get(position).category,
                    basketArray.get(position).image_url,
                    basketArray.get(position).rate
                )
            Navigation.findNavController(it).navigate(navArgs)
        }

        holder.dataBinding.recyclerRowBasketBtnDecrease.setOnClickListener {
            //Azalt
        }
        holder.dataBinding.recyclerRowBasketBtnIncrease.setOnClickListener {
            //Arttır
        }

    }
}