package com.example.aliwo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentProductCategoryRowBinding
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.listener.firebaselistener.IListExistListener
import com.example.aliwo.view.activity.LoginSignUpActivity
import com.example.aliwo.service.firebasedao.FirebaseDataAdder
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.IntentUtils
import com.example.aliwo.util.UIUtils
import com.example.aliwo.view.fragment.ProductCategoryFragmentDirections
import com.google.firebase.firestore.FieldValue
import java.math.BigDecimal
import java.math.RoundingMode

class ProductCategoryAdapter(
    val context: Context,
    private val productList: List<ApiProductsModel>, private val fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryAllVH>() {
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val firebaseDataController = FirebaseDataController()
    private val firebaseDataAdder = FirebaseDataAdder()
    private val firebaseUserManager = FirebaseUserManager()
    private val intentUtils = IntentUtils()
    private val uIUtil = UIUtils()

    class ProductCategoryAllVH(val dataBinding: FragmentProductCategoryRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryAllVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentProductCategoryRowBinding>(
            inflater,
            R.layout.fragment_product_category_row,
            parent,
            false
        )
        return ProductCategoryAllVH(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ProductCategoryAllVH,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val arrayPosition = productList.get(position)
        holder.dataBinding.product = productList[position]

        val favoritesTgb = holder.dataBinding.rowProductCategoryAllTgbFavorites
        val basketTgb = holder.dataBinding.rowProductCategoryAllTgbAddBasket

        if (firebaseUserManager.currentUser()) {
            val listExistListener = object : IListExistListener {
                override fun controlFavoritesList(exists: Boolean) {
                    addDataFirebaseFavorites(favoritesTgb, position, exists)
                    favoritesTgb.isChecked = exists
                }

                override fun controlBasketList(exists: Boolean) {
                    addFirebaseBasket(basketTgb, position, exists)
                    basketTgb.isChecked = exists
                }

            }
            firebaseDataController.controlFireStoreDataBasket(
                productList.get(position).id, listExistListener
            )
            firebaseDataController.controlFireStoreDataFavorites(
                productList.get(position).id, listExistListener
            )

        } else {

            favoritesTgb.setBackgroundResource(R.drawable.asset_favorites)
            favoritesTgb.setOnClickListener {
                intentUtils.intentActivity(context, LoginSignUpActivity())
            }

            basketTgb.setBackgroundResource(R.drawable.add_basket_button_shape)
            basketTgb.textOn = context.getString(R.string.add_basket)
            basketTgb.setOnClickListener {
                intentUtils.intentActivity(context, LoginSignUpActivity())
            }

        }
        holder.dataBinding.rowProductCategoryAllCardView.setOnClickListener {
            val dataDrections =
                ProductCategoryFragmentDirections.actionProductCategoryFragmentToProductDetailFragment(
                    arrayPosition.id,
                    arrayPosition.title,
                    arrayPosition.price.toFloat(),
                    arrayPosition.description,
                    arrayPosition.category,
                    arrayPosition.image,
                    arrayPosition.rating.rate
                )


            Navigation.findNavController(it).navigate(dataDrections)
        }
    }


    fun addDataFirebaseFavorites(toggleButton: ToggleButton, position: Int, existFavorites: Boolean) {
        val bigDecimal = BigDecimal(productList.get(position).rating.rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to productList.get(position).id,
            "title" to productList.get(position).title,
            "price" to productList.get(position).price,
            "image" to productList.get(position).image,
            "rate" to bigDecimal.toDouble(),
            "description" to productList.get(position).description,
            "category" to productList.get(position).category,
            "timestamp" to FieldValue.serverTimestamp()
        )

        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                if (!existFavorites) {
                    firebaseDataAdder.addFireStoreDataFavorites(

                        productList.get(position).id,
                        productMap
                    )
                }

            } else {
                firebaseDataDeleter.deleteFireStoreDataFavorites(productList.get(position).id)
            }
        }
    }

    fun addFirebaseBasket(toggleButton: ToggleButton, position: Int, existBasket: Boolean) {
        val bigDecimal = BigDecimal(productList.get(position).rating.rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to productList.get(position).id,
            "title" to productList.get(position).title,
            "price" to productList.get(position).price,
            "image" to productList.get(position).image,
            "rate" to bigDecimal.toDouble(),
            "description" to productList.get(position).description,
            "category" to productList.get(position).category,
            "count" to 1,
            "timestamp" to FieldValue.serverTimestamp()
        )
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            uIUtil.badgeActivate(fragmentActivity)
            if (isChecked) {
                if (!existBasket) {
                    firebaseDataAdder.addFireStoreDataBasket(
                        productList.get(position).id,
                        productMap
                    )
                }
            } else {
                firebaseDataDeleter.deleteFireStoreDataBasket(productList.get(position).id)
            }
        }
    }

}