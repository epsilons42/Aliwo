package com.example.aliwo.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aliwo.adapters.BasketAdapter
import com.example.aliwo.databinding.FragmentBasketBinding
import com.example.aliwo.listener.IOnChangeAmount
import com.example.aliwo.model.ProductBasketModel
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.IntentUtils
import com.example.aliwo.util.NetworkUtils
import com.example.aliwo.view.activity.LoginSignUpActivity
import com.example.aliwo.viewmodel.BasketViewModel

class BasketFragment : Fragment() {
    private lateinit var viewBinding: FragmentBasketBinding
    private lateinit var basketViewModel: BasketViewModel
    private val productBasketArray = ArrayList<ProductBasketModel>()
    private val intentUtils = IntentUtils()
    private val firebaseUserManager = FirebaseUserManager()
    private val networkUtils = NetworkUtils()


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentBasketBinding.inflate(inflater, container, false)
        basketViewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        basketViewModel.controlCurrentUser()
        goToActivity()
        val network = networkUtils.isNetworkAvailable(requireContext())
        networkControlAndVisibility(network)
        return viewBinding.root
    }

    private fun networkControlAndVisibility(network: Boolean) {
        if (network) {
            currentUserControlAndVisibility()
            viewBinding.fragmentBasketImvWrong.visibility = View.GONE
            viewBinding.fragmentBasketTxvWrong.visibility = View.GONE
            viewBinding.fragmentBasketRecyclerView.visibility = View.VISIBLE

        } else {
            viewBinding.fragmentBasketImvWrong.visibility = View.VISIBLE
            viewBinding.fragmentBasketTxvWrong.visibility = View.VISIBLE
            viewBinding.fragmentBasketRecyclerView.visibility = View.GONE
        }
    }

    private fun currentUserControlAndVisibility() {
        if (firebaseUserManager.currentUser()) {
            viewModelObserve()
        } else {
            viewBinding.fragmentBasketBtnLogin.visibility = View.VISIBLE
            viewBinding.fragmentBasketImvBasket.visibility = View.VISIBLE
        }
    }

    private fun goToActivity() {
        viewBinding.fragmentBasketBtnLogin.setOnClickListener {
            intentUtils.intentActivity(requireContext(), LoginSignUpActivity())
        }
    }

    fun viewModelObserve() {
        basketViewModel.productMLD.observe(viewLifecycleOwner) { product ->
            product?.let {
                if (productBasketArray.size == 0) {
                    val onchangeAmount = object : IOnChangeAmount {
                        @SuppressLint("SetTextI18n")
                        override fun onChange(totalPrice: Double) {
                            val stringFormat = String.format("%.2f", totalPrice)
                            viewBinding.fragmentBasketTxvTotalPrice.text = stringFormat
                            if (totalPrice != 0.0) {
                                viewBinding.fragmentBasketCardViewPrice.visibility = View.VISIBLE
                            } else {
                                viewBinding.fragmentBasketCardViewPrice.visibility = View.GONE
                            }
                        }
                    }
                    viewBinding.fragmentBasketRecyclerView.adapter =
                        BasketAdapter(
                            requireContext(),
                            it,
                            requireActivity(),
                            onchangeAmount
                        )
                    viewBinding.fragmentBasketRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                }
            }
        }
    }
}