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
import com.example.aliwo.databinding.FragmentProductSearchRowBinding
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.listener.firebaselistener.IListExistListener
import com.example.aliwo.view.activity.LoginSignUpActivity
import com.example.aliwo.view.fragment.ProductSearchFragmentDirections
import com.example.aliwo.service.firebasedao.FirebaseDataAdder
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.IntentUtils
import com.example.aliwo.util.UIUtils
import com.google.firebase.firestore.FieldValue
import java.math.BigDecimal
import java.math.RoundingMode

class ProductSearchAdapter(
    val context: Context,
    private var productArrayList: ArrayList<ApiProductsModel>, private val fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<ProductSearchAdapter.ProductSearchVH>() {
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val firebaseDataController = FirebaseDataController()
    private val firebaseDataAdder = FirebaseDataAdder()
    private val firebaseUserManager = FirebaseUserManager()
    private val intentUtils = IntentUtils()
    private val uIUtil = UIUtils()

    class ProductSearchVH(val dataBinding: FragmentProductSearchRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSearchVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentProductSearchRowBinding>(
            inflater,
            R.layout.fragment_product_search_row,
            parent,
            false
        )
        return ProductSearchVH(view)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    override fun onBindViewHolder(
        holder: ProductSearchVH,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val arrayPosition = productArrayList.get(position)
        holder.dataBinding.product = productArrayList[position]

        val favoritesTgb = holder.dataBinding.rowProductSearchTgbFavorites
        val basketTgb = holder.dataBinding.rowProductSearchTgbAddBasket

        if (firebaseUserManager.currentUser()) {
            val listExistListener = object : IListExistListener {
                override fun controlFavoritesList(exists: Boolean) {
                    addDataFirebaseFavorites(favoritesTgb, position, exists)
                    favoritesTgb.isChecked = exists
                }

                override fun controlBasketList(exists: Boolean) {
                    addDataFirebaseBasket(basketTgb, position, exists)
                    basketTgb.isChecked = exists
                }

            }
            firebaseDataController.controlFireStoreDataBasket(
                productArrayList.get(position).id, listExistListener
            )
            firebaseDataController.controlFireStoreDataFavorites(
                productArrayList.get(position).id, listExistListener
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

        holder.dataBinding.rowProductSearchCardView.setOnClickListener {
            val dataDrections =
                ProductSearchFragmentDirections.actionProductSearchFragmentToProductDetailFragment(
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
        val bigDecimal = BigDecimal(productArrayList.get(position).rating.rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to productArrayList.get(position).id,
            "title" to productArrayList.get(position).title,
            "price" to productArrayList.get(position).price,
            "image" to productArrayList.get(position).image,
            "rate" to bigDecimal.toDouble(),
            "description" to productArrayList.get(position).description,
            "category" to productArrayList.get(position).category,
            "timestamp" to FieldValue.serverTimestamp()
        )
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                if (!existFavorites) {
                    firebaseDataAdder.addFireStoreDataFavorites(
                        productArrayList.get(position).id,
                        productMap
                    )
                }

            } else {
                firebaseDataDeleter.deleteFireStoreDataFavorites(productArrayList.get(position).id)
            }
        }
    }

    fun addDataFirebaseBasket(toggleButton: ToggleButton, position: Int, existBasket: Boolean) {
        val bigDecimal = BigDecimal(productArrayList.get(position).rating.rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to productArrayList.get(position).id,
            "title" to productArrayList.get(position).title,
            "price" to productArrayList.get(position).price,
            "image" to productArrayList.get(position).image,
            "rate" to bigDecimal.toDouble(),
            "description" to productArrayList.get(position).description,
            "category" to productArrayList.get(position).category,
            "count" to 1,
            "timestamp" to FieldValue.serverTimestamp()
        )
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            uIUtil.badgeActivate(fragmentActivity)
            if (isChecked) {
                if (!existBasket) {
                    firebaseDataAdder.addFireStoreDataBasket(
                        productArrayList.get(position).id,
                        productMap
                    )
                }
            } else {
                firebaseDataDeleter.deleteFireStoreDataBasket(productArrayList.get(position).id)
            }
        }
    }

}

