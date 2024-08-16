package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aliwo.adapters.ProductParentAdapter
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentProductChildBinding
import com.example.aliwo.model.ProductRVParentModel
import com.example.aliwo.viewmodel.ProductChildViewModel
import com.example.aliwo.util.NetworkUtils
import com.example.aliwo.util.PreferencesUtils

class ProductChildFragment : Fragment() {
    private lateinit var viewBinding: FragmentProductChildBinding
    private lateinit var productChildViewModel: ProductChildViewModel
    private val productChildArrayList = ArrayList<ProductRVParentModel>()
    private val preferencesUtils = PreferencesUtils()
    private val networkUtils = NetworkUtils()

    /*Clicking the radiobutton in the product search and categories section is saved,
     when you come to the home page,
      this record is deleted and returns to the default values
     */
    override fun onStart() {
        super.onStart()
        preferencesUtils.sortRadioButtonRefresh(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentProductChildBinding.inflate(inflater, container, false)
        productChildViewModel = ViewModelProvider(this).get(ProductChildViewModel::class.java)
        productChildViewModel.productListSizeControl(requireContext())
        viewModelObserve()
        recyclerViewActions()
        viewBinding.fragmentProductChildSearchView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_productChildFragment_to_searchFragment)
        }

        viewBinding.fragmentProductChildImbNotification.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_productChildFragment_to_notificationFragment)
        }

        if (networkUtils.isNetworkAvailable(requireContext())) {
            viewBinding.fragmentProductChildPgb.visibility = View.VISIBLE
            viewBinding.fragmentProductChildImvWrong.visibility = View.GONE
            viewBinding.fragmentProductChildTxvWrong.visibility = View.GONE
            viewBinding.fragmentProductChildRecyclerView.visibility = View.VISIBLE

        } else {
            viewBinding.fragmentProductChildPgb.visibility = View.GONE
            viewBinding.fragmentProductChildImvWrong.visibility = View.VISIBLE
            viewBinding.fragmentProductChildTxvWrong.visibility = View.VISIBLE
            viewBinding.fragmentProductChildRecyclerView.visibility = View.GONE
        }


        viewBinding.fragmentProductChildSearchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Navigation.findNavController(v)
                    .navigate(R.id.action_productChildFragment_to_searchFragment)
            }
        }
        return viewBinding.root
    }

    fun viewModelObserve() {
        productChildViewModel.productParentMLD.observe(viewLifecycleOwner) { product ->
            product?.let {
                if(productChildArrayList.size == 0){
                    productChildArrayList.addAll(it)
                    recyclerViewActions()
                }

            }
        }
        productChildViewModel.loadMLD.observe(viewLifecycleOwner) { load ->
            load?.let {
                if (it) {
                    viewBinding.fragmentProductChildPgb.visibility = View.GONE

                }
            }
        }
    }

    private fun recyclerViewActions() {

            viewBinding.fragmentProductChildRecyclerView.adapter =
                ProductParentAdapter(requireContext(), productChildArrayList)
            viewBinding.fragmentProductChildRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())

    }
}