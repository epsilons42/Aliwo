package com.example.aliwo.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aliwo.adapters.ProductCategoryAdapter
import com.example.aliwo.R
import com.example.aliwo.databinding.FragmentProductCategoryBinding
import com.example.aliwo.model.ApiProductsModel
import com.example.aliwo.util.backIcon
import com.example.aliwo.util.navigationBackClick
import com.example.aliwo.viewmodel.ProductCategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.collections.ArrayList

class ProductCategoryFragment : Fragment() {
    private lateinit var viewBinding: FragmentProductCategoryBinding
    private lateinit var productCategoryViewModel: ProductCategoryViewModel
    private val productArrayList = ArrayList<ApiProductsModel>()
    private val bundle: ProductCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentProductCategoryBinding.inflate(inflater, container, false)

        productCategoryViewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        productCategoryViewModel.productListSizeControl(bundle.category)

        viewModelObserve()
        toolbarActions()

        recyclerViewActions()




        viewBinding.fragmentProductCategoryAllBtnSort.setOnClickListener {
            bottomSheetDialogAction()
        }
        return viewBinding.root
    }

    fun viewModelObserve() {
        productCategoryViewModel.productMLD.observe(
            viewLifecycleOwner
        ) { product ->
            product?.let {
                if(productArrayList.size == 0){
                    productArrayList.addAll(it)
                }
            }
        }

        productCategoryViewModel.productLoadMLD.observe(
            viewLifecycleOwner
        ) { productLoad ->
            productLoad?.let {
                if (it) {
                    viewBinding.fragmentProductCategoryAllBtnSort.visibility = View.VISIBLE
                    viewBinding.fragmentProductCategoryAllPgb.visibility = View.GONE

                } else {
                    viewBinding.fragmentProductCategoryAllPgb.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun toolbarActions() {
        val toolbar = viewBinding.fragmentProductCategoryAllToolbar
        toolbar.title = bundle.categoryTitle
        toolbar.backIcon()
        toolbar.navigationBackClick()
    }

    @SuppressLint("CommitPrefEdits", "NotifyDataSetChanged", "InflateParams")
    private fun bottomSheetDialogAction() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        val bottomSheetView = layoutInflater.inflate(R.layout.sort_bottom_sheet, null)
        val sharedPreferences =
            requireContext().getSharedPreferences("radioButtonClick", Context.MODE_PRIVATE)
        val sharedPreferencesEditor = sharedPreferences.edit()

        val imbCancel = bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetImbCancel) as ImageButton

        val radioGroup =
            bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetRg) as RadioGroup
        bottomSheetDialog.setContentView(bottomSheetView)

        val radioButton1 =
            bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetRbDefaultSorting) as RadioButton
        val radioButton2 =
            bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetRbLowestPrice) as RadioButton
        val radioButton3 =
            bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetRbHighestPrice) as RadioButton
        val radioButton4 =
            bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetRbLowestRated) as RadioButton
        val radioButton5 =
            bottomSheetView.findViewById(R.id.fragmentCategoryBottomSheetRbHighestRated) as RadioButton

        //Radio button remembers the saved click id
        when (sharedPreferences.getInt("rbClickId", 0)) {
            1 -> radioButton1.isChecked = true
            2 -> radioButton2.isChecked = true
            3 -> radioButton3.isChecked = true
            4 -> radioButton4.isChecked = true
            5 -> radioButton5.isChecked = true
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.fragmentCategoryBottomSheetRbDefaultSorting -> {
                    sharedPreferencesEditor.putInt("rbClickId", 1)
                    sharedPreferencesEditor.apply()
                    if (productArrayList.size != 0) {
                        recyclerViewActions()
                        productArrayList.clear()
                        viewModelObserve()
                        bottomSheetDialog.dismiss()
                    }
                }
                R.id.fragmentCategoryBottomSheetRbLowestPrice -> {
                    sharedPreferencesEditor.putInt("rbClickId", 2)
                    sharedPreferencesEditor.apply()
                    if (productArrayList.size != 0) {
                        recyclerViewActions()
                        productArrayList.sortBy {
                            it.price
                        }
                        bottomSheetDialog.dismiss()
                    }
                }
                R.id.fragmentCategoryBottomSheetRbHighestPrice -> {
                    sharedPreferencesEditor.putInt("rbClickId", 3)
                    sharedPreferencesEditor.apply()
                    if (productArrayList.size != 0) {
                        recyclerViewActions()
                        productArrayList.sortBy {
                            it.price
                        }
                        productArrayList.reverse()
                        bottomSheetDialog.dismiss()
                    }
                }
                R.id.fragmentCategoryBottomSheetRbLowestRated -> {
                    sharedPreferencesEditor.putInt("rbClickId", 4)
                    sharedPreferencesEditor.apply()
                    if (productArrayList.size != 0) {
                        recyclerViewActions()
                        productArrayList.sortBy {
                            it.rating.rate
                        }
                        bottomSheetDialog.dismiss()
                    }
                }
                R.id.fragmentCategoryBottomSheetRbHighestRated -> {
                    sharedPreferencesEditor.putInt("rbClickId", 5)
                    sharedPreferencesEditor.apply()
                    if (productArrayList.size != 0) {
                        recyclerViewActions()
                        productArrayList.sortBy {
                            it.rating.rate
                        }
                        productArrayList.reverse()
                        bottomSheetDialog.dismiss()
                    }

                }
            }
        }

        imbCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }

    fun recyclerViewActions() {
        viewBinding.fragmentProductCategoryAllRecyclerView.adapter =
            context?.let {
                ProductCategoryAdapter(
                    it,
                    productArrayList,requireActivity()
                )
            }
        viewBinding.fragmentProductCategoryAllRecyclerView.layoutManager =
            GridLayoutManager(context, 2)
        viewBinding.fragmentProductCategoryAllToolbar.title = bundle.categoryTitle
    }
}