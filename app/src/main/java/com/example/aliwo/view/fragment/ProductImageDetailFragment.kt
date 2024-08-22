package com.example.aliwo.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.aliwo.R
import com.example.aliwo.adapters.ImageDetailViewPager2Adapter
import com.example.aliwo.databinding.FragmentProductImageDetailBinding
import com.example.aliwo.listener.IZoomControlListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductImageDetailFragment : Fragment() {
    private lateinit var viewBinding: FragmentProductImageDetailBinding
    private val bundle: ProductImageDetailFragmentArgs by navArgs()


    override fun onStart() {
        super.onStart()
        val mainBottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.activityMainBottomNavigationView)
        mainBottomNavigationView.visibility = View.GONE
    }

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentProductImageDetailBinding.inflate(inflater, container, false)


        val viewPager2 = viewBinding.fragmentProductPhotoDetailVP2
        val tabLayout = viewBinding.fragmentProductPhotoDetailTL

        val zoomControlListener = object : IZoomControlListener {
            override fun zoomControl(zoom: Boolean) {
                viewPager2.isUserInputEnabled = !zoom
            }
        }

        viewPager2.adapter =
            ImageDetailViewPager2Adapter(
                requireContext(),
                bundle.imageUrlList.toList(),
                zoomControlListener
            )
        viewPager2.setCurrentItem(bundle.position, false)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            val tabView =
                LayoutInflater.from(requireContext()).inflate(R.layout.image_tablayout_item, null)
            val tabImage = tabView.findViewById(R.id.imageTabLayoutItemImv) as ImageView
            Glide.with(this).load(bundle.imageUrlList.get(position)).into(tabImage)
            tab.customView = tabImage
        }.attach()

        viewBinding.fragmentProductPhotoDetailImb.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        return viewBinding.root
    }
}