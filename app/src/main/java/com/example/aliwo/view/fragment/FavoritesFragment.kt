package com.example.aliwo.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aliwo.adapterss.FavoritesAdapter
import com.example.aliwo.databinding.FragmentFavoritesBinding
import com.example.aliwo.view.activity.LoginSignUpActivity
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import com.example.aliwo.util.IntentUtils
import com.example.aliwo.util.NetworkUtils
import com.example.aliwo.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {
    private lateinit var viewBinding: FragmentFavoritesBinding
    private lateinit var favoritesViewModel: FavoritesViewModel
    private val networkUtils = NetworkUtils()
    private val intentUtils = IntentUtils()
    private val firebaseUserManager = FirebaseUserManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        favoritesViewModel.controlCurrentUser()
        goToActivity()
        val network = networkUtils.isNetworkAvailable(requireContext())
        networkControlAndVisibility(network)

        return viewBinding.root
    }

    private fun networkControlAndVisibility(network : Boolean){
        if (network) {
            currentUserControlAndVisibility()
            viewBinding.fragmentFavoritesImvWrong.visibility = View.GONE
            viewBinding.fragmentFavoritesTxvWrong.visibility = View.GONE
            viewBinding.fragmentFavoritesRecyclerView.visibility = View.VISIBLE

        } else {
            viewBinding.fragmentFavoritesImvWrong.visibility = View.VISIBLE
            viewBinding.fragmentFavoritesTxvWrong.visibility = View.VISIBLE
            viewBinding.fragmentFavoritesRecyclerView.visibility = View.GONE
        }
    }
    private fun currentUserControlAndVisibility(){
        //network true
        if (firebaseUserManager.currentUser()) {
            viewModelObserve()
        } else {
            viewBinding.fragmentFavoritesBtnLogin.visibility = View.VISIBLE
            viewBinding.fragmentFavoritesImvBasket.visibility = View.VISIBLE
        }
    }

    private fun goToActivity(){
        viewBinding.fragmentFavoritesBtnLogin.setOnClickListener {
            intentUtils.intentActivity(requireContext(), LoginSignUpActivity())
        }
    }

    fun viewModelObserve() {
        favoritesViewModel.productMLD.observe(viewLifecycleOwner) { product ->
            product?.let {
                viewBinding.fragmentFavoritesRecyclerView.adapter =
                    FavoritesAdapter(requireContext(), it,requireActivity())
                viewBinding.fragmentFavoritesRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
            }
        }
    }
}