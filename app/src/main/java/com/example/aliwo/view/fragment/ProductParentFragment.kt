package com.example.aliwo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aliwo.databinding.FragmentProductParentBinding

class ProductParentFragment : Fragment() {
    private lateinit var viewBinding: FragmentProductParentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentProductParentBinding.inflate(inflater, container, false)

        return viewBinding.root
    }
}



