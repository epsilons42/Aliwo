package com.example.aliwo.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.aliwo.adapters.ProductDetailViewPager2Adapter
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentProductDetailBinding
import com.example.aliwo.model.ProductDetailModel
import com.example.aliwo.util.*
import com.example.aliwo.view.activity.LoginSignUpActivity
import com.example.aliwo.service.firebasedao.FirebaseDataAdder
import com.example.aliwo.service.firebasedao.FirebaseDataController
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.listener.firebaselistener.IListExistListener
import com.example.aliwo.util.IntentUtils
import com.example.aliwo.util.UIUtils
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FieldValue
import java.math.BigDecimal
import java.math.RoundingMode

class ProductDetailFragment : Fragment() {
    private lateinit var dataBinding: FragmentProductDetailBinding
    private val bundle: ProductDetailFragmentArgs by navArgs()
    private val productList = ArrayList<ProductDetailModel>()
    private val firebaseDataDeleter = FirebaseDataDeleter()
    private val firebaseDataController = FirebaseDataController()
    private val firebaseDataAdder = FirebaseDataAdder()
    private val firebaseUserManager = FirebaseUserManager()
    private val intentUtils = IntentUtils()
    private val uIUtil = UIUtils()
    private val imageUrlList = ArrayList<String>()

    override fun onPause() {
        super.onPause()
        val mainBottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.activityMainBottomNavigationView)
        mainBottomNavigationView.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        val mainBottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.activityMainBottomNavigationView)
        mainBottomNavigationView.visibility = View.GONE
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)


        val bigDecimal = BigDecimal(bundle.price.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        productList.add(
            ProductDetailModel(
                bundle.id,
                bundle.title,
                bigDecimal.toDouble(),
                bundle.description,
                bundle.category,
                bundle.image,
                bundle.rate
            )
        )
        dataBinding.product = productList.get(0)

        val favoritesTgb = dataBinding.fragmentProductDetailTgbAddFavorites
        val basketTgb = dataBinding.fragmentProductDetailTgbAddBasket

        if (productList.size != 0) {
            dataBinding.fragmentProductDetailCl.visibility = View.VISIBLE
            dataBinding.fragmentProductDetailCardView.visibility = View.VISIBLE
            dataBinding.fragmentProductDetailPgb.visibility = View.GONE
        } else {
            dataBinding.fragmentProductDetailCl.visibility = View.GONE
            dataBinding.fragmentProductDetailCardView.visibility = View.GONE
            dataBinding.fragmentProductDetailPgb.visibility = View.VISIBLE
        }

        if (firebaseUserManager.currentUser()) {
            val listExistListener = object : IListExistListener {
                override fun controlFavoritesList(exists: Boolean) {
                    addDataFavorites(exists)
                    favoritesTgb.isChecked = exists
                }

                override fun controlBasketList(exists: Boolean) {
                    addDataBasket(exists)
                    basketTgb.isChecked = exists
                }

            }
            firebaseDataController.controlFireStoreDataBasket(
                bundle.id, listExistListener
            )
            firebaseDataController.controlFireStoreDataFavorites(
                bundle.id, listExistListener
            )
        } else {
            favoritesTgb.setBackgroundResource(R.drawable.asset_favorites)
            favoritesTgb.setOnClickListener {
                intentUtils.intentActivity(requireContext(), LoginSignUpActivity())
            }
            basketTgb.setBackgroundResource(R.drawable.add_basket_button_shape)

            basketTgb.setOnClickListener {
                intentUtils.intentActivity(requireContext(), LoginSignUpActivity())
            }
        }
        imageUrlList.add(bundle.image)
        imageUrlList.add(bundle.image)
        imageUrlList.add(bundle.image)
        val viewPager2 = dataBinding.fragmentProductDetaiVP2
        val tableLayout = dataBinding.fragmentProductDetailTBL
       viewPager2.adapter = ProductDetailViewPager2Adapter(imageUrlList)

        TabLayoutMediator(tableLayout, viewPager2) { tab, position ->

        }.attach()



        dataBinding.fragmentProductDetailTxvCategory.setOnClickListener {
            val navDirections =
                ProductDetailFragmentDirections.actionProductDetailFragmentToProductSearchFragment(
                    bundle.category
                )
            Navigation.findNavController(it).navigate(navDirections)

        }
        toolbarActions()
        return dataBinding.root
    }

    private fun addDataFavorites(existFavorites: Boolean) {
        val bigDecimal = BigDecimal(bundle.rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to bundle.id,
            "title" to bundle.title,
            "price" to bundle.price,
            "image" to bundle.image,
            "rate" to bigDecimal.toDouble(),
            "description" to bundle.description,
            "category" to bundle.category,
            "timestamp" to FieldValue.serverTimestamp()
        )
        dataBinding.fragmentProductDetailTgbAddFavorites.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                if (!existFavorites) {
                    firebaseDataAdder.addFireStoreDataFavorites(

                        bundle.id,
                        productMap
                    )
                }

            } else {
                firebaseDataDeleter.deleteFireStoreDataFavorites(bundle.id)
            }
        }
    }

    fun addDataBasket(existBasket: Boolean) {
        val bigDecimal = BigDecimal(bundle.rate.toDouble()).setScale(
            2,
            RoundingMode.HALF_EVEN
        )
        val productMap = mapOf(
            "id" to bundle.id,
            "title" to bundle.title,
            "price" to bundle.price,
            "image" to bundle.image,
            "rate" to bigDecimal.toDouble(),
            "description" to bundle.description,
            "category" to bundle.category,
            "count" to 1,
            "timestamp" to FieldValue.serverTimestamp()
        )
        dataBinding.fragmentProductDetailTgbAddBasket.setOnCheckedChangeListener { buttonView, isChecked ->
            uIUtil.badgeActivate(requireActivity())
            if (isChecked) {
                if (!existBasket) {
                    firebaseDataAdder.addFireStoreDataBasket(
                        bundle.id,
                        productMap
                    )
                }
            } else {
                firebaseDataDeleter.deleteFireStoreDataBasket(bundle.id)
            }
        }

    }
    private fun toolbarActions() {
        val toolbar = dataBinding.fragmentProductDetailToolbar
        toolbar.title = getString(R.string.product_detail)
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }

}
