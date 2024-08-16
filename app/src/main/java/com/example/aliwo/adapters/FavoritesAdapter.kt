package com.example.aliwo.adapterss

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
import com.example.aliwo.databinding.FragmentFavoritesRowBinding
import com.example.aliwo.listener.firebaselistener.IListExistListener
import com.example.aliwo.model.ProductFavoritesModel
import com.example.aliwo.view.fragment.FavoritesFragmentDirections
import com.example.aliwo.service.firebasedao.FirebaseDataAdder
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.util.UIUtils
import com.google.firebase.firestore.FieldValue
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.collections.ArrayList

class FavoritesAdapter(val context: Context, val favoritesArray: ArrayList<ProductFavoritesModel>, val fragmentActivity : FragmentActivity
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesVH>() {
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val firebaseDataController = FirebaseDataController()
    private val firebaseDataAdder = FirebaseDataAdder()
    private val uIUtil = UIUtils()


    class FavoritesVH(val dataBinding: FragmentFavoritesRowBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FragmentFavoritesRowBinding>(
            inflater,
            R.layout.fragment_favorites_row,
            parent,
            false
        )
        return FavoritesVH(view)
    }

    override fun getItemCount(): Int {
        return favoritesArray.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: FavoritesVH,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.dataBinding.product = favoritesArray[position]
        holder.dataBinding.recyclerRowFavoritesBtnFavorites.setOnClickListener {
            firebaseDataDeleter.deleteFireStoreDataFavorites(favoritesArray.get(position).id)
            favoritesArray.removeAt(position)
            notifyDataSetChanged()

        }

        val basketTgb = holder.dataBinding.recyclerRowFavoritesTgbAddBasket
        val listExistListener = object : IListExistListener {
            override fun controlFavoritesList(exists: Boolean) {
            }

            override fun controlBasketList(exists: Boolean) {
                addDataFirebaseBasket(basketTgb, position, exists)
                basketTgb.isChecked = exists
            }
        }

        firebaseDataController.controlFireStoreDataBasket(
            favoritesArray.get(position).id, listExistListener
        )

        holder.dataBinding.recyclerRowFavoritesCardViewProduct.setOnClickListener {
            val navArgs =
                FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailFragment(
                    favoritesArray.get(position).id,
                    favoritesArray.get(position).title,
                    favoritesArray.get(position).price.toFloat(),
                    favoritesArray.get(position).description,
                    favoritesArray.get(position).category,
                    favoritesArray.get(position).image_url,
                    favoritesArray.get(position).rate
                )
            Navigation.findNavController(it).navigate(navArgs)
        }
    }

    fun addDataFirebaseBasket(toggleButton: ToggleButton, position: Int, existBasket: Boolean) {
        val bigDecimal = BigDecimal(favoritesArray.get(position).rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to favoritesArray.get(position).id,
            "title" to favoritesArray.get(position).title,
            "price" to favoritesArray.get(position).price,
            "image" to favoritesArray.get(position).image_url,
            "rate" to bigDecimal.toDouble(),
            "description" to favoritesArray.get(position).description,
            "category" to favoritesArray.get(position).category,
            "count" to 1,
            "timestamp" to FieldValue.serverTimestamp()
        )
        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            uIUtil.badgeActivate(fragmentActivity)
            if (isChecked) {
                if (!existBasket) {
                    firebaseDataAdder.addFireStoreDataBasket(
                        favoritesArray.get(position).id,
                        productMap
                    )
                }
            } else {
                firebaseDataDeleter.deleteFireStoreDataBasket(favoritesArray.get(position).id)
            }
        }
    }
}

