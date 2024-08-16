package com.example.aliwo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentProductChildRowBinding
import com.example.aliwo.model.ProductRVChildModel
import com.example.aliwo.listener.firebaselistener.IListExistListener
import com.example.aliwo.view.activity.LoginSignUpActivity
import com.example.aliwo.view.fragment.ProductChildFragmentDirections
import com.example.aliwo.service.firebasedao.FirebaseDataAdder
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.IntentUtils
import com.google.firebase.firestore.FieldValue
import java.math.BigDecimal
import java.math.RoundingMode

class ProductChildAdapter(
    val context: Context,
    private val productChildList: List<ProductRVChildModel>
) :
    RecyclerView.Adapter<ProductChildAdapter.ProductChildVH>() {
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val firebaseDataController = FirebaseDataController()
    private val firebaseDataAdder = FirebaseDataAdder()
    private val firebaseUserManager = FirebaseUserManager()
    private val intentUtils = IntentUtils()

    class ProductChildVH(val dataBinding: FragmentProductChildRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductChildVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentProductChildRowBinding>(
            inflater,
            R.layout.fragment_product_child_row,
            parent,
            false
        )
        return ProductChildVH(view)
    }

    override fun getItemCount(): Int {
        return productChildList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ProductChildVH,
        @SuppressLint("RecyclerView") position: Int
    ) {

        holder.dataBinding.product = productChildList[position]
        val favoritesTgb = holder.dataBinding.rowProductChildTgbAddFavorites

        if (firebaseUserManager.currentUser()) {
            val listExistListener = object : IListExistListener {
                override fun controlFavoritesList(exists: Boolean) {
                    addDataFirebaseFavorites(favoritesTgb, position, exists)
                    favoritesTgb.isChecked = exists
                }

                override fun controlBasketList(exists: Boolean) {
                }

            }
            firebaseDataController.controlFireStoreDataFavorites(
                productChildList.get(position).productId, listExistListener
            )
        } else {
            favoritesTgb.setBackgroundResource(R.drawable.asset_favorites_white)
            favoritesTgb.setOnClickListener {
                intentUtils.intentActivity(context, LoginSignUpActivity())
            }

        }


        holder.dataBinding.rowProductChildCardView.setOnClickListener {
            val navARg = ProductChildFragmentDirections.toProductDetail(
                productChildList.get(position).productId,
                productChildList.get(position).productName,
                productChildList.get(position).productPrice.toFloat(),
                productChildList.get(position).description,
                productChildList.get(position).category,
                productChildList.get(position).productImage,
                productChildList.get(position).productRating
            )
            Navigation.findNavController(it).navigate(navARg)
        }
//

    }

    fun addDataFirebaseFavorites(
        toggleButton: ToggleButton,
        position: Int,
        existFavorites: Boolean
    ) {
        val bigDecimal =
            BigDecimal(productChildList.get(position).productRating.toDouble()).setScale(
                2,
                RoundingMode.HALF_EVEN
            )
        val productMap = mapOf(
            "id" to productChildList.get(position).productId,
            "title" to productChildList.get(position).productName,
            "price" to productChildList.get(position).productPrice,
            "image" to productChildList.get(position).productImage,
            "rate" to bigDecimal.toDouble(),
            "description" to productChildList.get(position).description,
            "category" to productChildList.get(position).category,
            "timestamp" to FieldValue.serverTimestamp()
        )
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                if (!existFavorites) {
                    firebaseDataAdder.addFireStoreDataFavorites(
                        productChildList.get(position).productId,
                        productMap
                    )
                }

            } else {
                firebaseDataDeleter.deleteFireStoreDataFavorites(productChildList.get(position).productId)
            }
        }
    }
}

