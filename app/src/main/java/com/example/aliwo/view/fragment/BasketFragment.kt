package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aliwo.adapters.BasketAdapter
import com.example.aliwo.databinding.FragmentBasketBinding
import com.example.aliwo.viewmodel.BasketViewModel

class BasketFragment : Fragment() {
    private lateinit var viewBinding: FragmentBasketBinding
    private lateinit var basketViewModel: BasketViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentBasketBinding.inflate(inflater, container, false)
        basketViewModel = ViewModelProvider(this).get(BasketViewModel::class.java)
        basketViewModel.controlCurrentUser()
        viewModelObserve()

        return viewBinding.root

    }
    fun viewModelObserve() {
        basketViewModel.productMLD.observe(viewLifecycleOwner) { product ->
            product?.let {
                viewBinding.fragmentBasketRecyclerView.adapter =
                    BasketAdapter(requireContext(), it,requireActivity())
                viewBinding.fragmentBasketRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())

            }
        }
    }
}
