package com.example.aliwo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentBasketRowBinding
import com.example.aliwo.listener.IOnChangeAmount
import com.example.aliwo.listener.firebaselistener.IUpdateListener
import com.example.aliwo.model.ProductBasketModel
import com.example.aliwo.view.fragment.BasketFragmentDirections
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.service.firebasedao.FirebaseDataUpdater
import com.example.aliwo.util.UIUtils


class BasketAdapter(
    val context: Context,
    private val basketArray: ArrayList<ProductBasketModel>,
    private val fragmentActivity: FragmentActivity,
    private val onChangeAmount: IOnChangeAmount
) :
    RecyclerView.Adapter<BasketAdapter.BasketVH>() {
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val firebaseDataUpdater = FirebaseDataUpdater()
    private val uIUtil = UIUtils()

    class BasketVH(val dataBinding: FragmentBasketRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

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
        holder.dataBinding.product = basketArray[holder.adapterPosition]
        totalPrice()
        val count = basketArray[holder.adapterPosition].count
        val price = basketArray[holder.adapterPosition].price

        val result = count * price
        val stringFormat = String.format("%.2f", result)
        holder.dataBinding.recyclerRowBasketTxvPrice.text = stringFormat
        holder.dataBinding.recyclerRowBasketBtnDelete.setOnClickListener {
            uIUtil.badgeActivate(fragmentActivity)
            firebaseDataDeleter.deleteFireStoreDataBasket(basketArray[holder.adapterPosition].id)
            basketArray.removeAt(holder.adapterPosition)
            notifyDataSetChanged()
            if (basketArray.size == 0) {
                onChangeAmount.onChange(0.0)
            }
        }

        holder.dataBinding.recyclerRowBasketCardViewProduct.setOnClickListener {
            val navArgs =
                BasketFragmentDirections.actionBasketFragmentToProductDetailFragment(
                    basketArray[holder.adapterPosition].id,
                    basketArray[holder.adapterPosition].title,
                    basketArray[holder.adapterPosition].price.toFloat(),
                    basketArray[holder.adapterPosition].description,
                    basketArray[holder.adapterPosition].category,
                    basketArray[holder.adapterPosition].image_url,
                    basketArray[holder.adapterPosition].rate
                )
            Navigation.findNavController(it).navigate(navArgs)
        }


        holder.dataBinding.recyclerRowBasketBtnDecrease.setOnClickListener {
            val newCount = count - 1
            if (count > 1) {
                returnAfterCountChange(holder, newCount)
            }
        }
        holder.dataBinding.recyclerRowBasketBtnIncrease.setOnClickListener {
            val newCount = count + 1
            if (count < 20) {
                returnAfterCountChange(holder, newCount)
            }
        }

    }

    private fun returnAfterCountChange(holder: BasketVH, newCount: Int) {
        val basketPgb = holder.dataBinding.recyclerRowBasketPgb
        val txvCount = holder.dataBinding.recyclerRowBasketTxvCount
        val updateListener = object : IUpdateListener {
            override fun userInfoUpdateListener(update: Boolean) {
                returnAfterUpdateControl(holder, update, newCount)
            }
        }
        basketPgb.visibility = View.VISIBLE
        txvCount.visibility = View.INVISIBLE
        firebaseDataUpdater.updateFireStoreBasketCount(
            basketArray[holder.adapterPosition].id,
            newCount, updateListener
        )
    }

    private fun returnAfterUpdateControl(holder: BasketVH, boolean: Boolean, newCount: Int) {
        val count = basketArray[holder.adapterPosition].count
        val basketPgb = holder.dataBinding.recyclerRowBasketPgb
        val txvCount = holder.dataBinding.recyclerRowBasketTxvCount
        if (boolean) {
            basketPgb.visibility = View.GONE
            txvCount.visibility = View.VISIBLE
            basketArray[holder.adapterPosition].count = newCount
            holder.dataBinding.product = basketArray[holder.adapterPosition]
            notifyItemChanged(holder.adapterPosition)
        } else {
            basketPgb.visibility = View.GONE
            txvCount.visibility = View.VISIBLE
            basketArray[holder.adapterPosition].count = count
            holder.dataBinding.product = basketArray[holder.adapterPosition]
        }
    }

    private fun totalPrice() {
        var total = 0.0
        for (basketList in basketArray) {
            total += basketList.price * basketList.count
            onChangeAmount.onChange(total)
        }
    }
}