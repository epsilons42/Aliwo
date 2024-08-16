package com.example.aliwo.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aliwo.adapters.SearchAdapter
import com.example.aliwo.databinding.FragmentSearchBinding
import com.example.aliwo.viewmodel.SearchViewModel
import com.example.aliwo.util.PreferencesUtils
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: SearchAdapter
    private lateinit var viewBinding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private val preferencesUtils = PreferencesUtils()
    private val searchItemList = HashSet<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        preferencesUtils.sortRadioButtonRefresh(requireContext())

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.searchItemLoadData()
        viewModelObserve()
        searchProduct()
        openKeyboard(searchView)

        searchView.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }


        recyclerView = viewBinding.fragmentSearchRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchAdapter(searchItemList.toList())
        recyclerView.adapter = adapter

        return viewBinding.root
    }
    private fun openKeyboard(searchView : SearchView){
        searchView.viewTreeObserver.addOnGlobalLayoutListener(object:
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                searchView.requestFocus()
                val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(searchView, 0)
                searchView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun searchProduct() {
        searchView = viewBinding.fragmentSearchSchv
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //The entered data is confirmed and sent to the product search page
                if (query != null) {
                    if (query.length >= 2) {
                        val dataDirections =
                            SearchFragmentDirections.actionSearchFragmentToProductSearchFragment(
                                query
                            )
                        view?.let {
                            Navigation.findNavController(it).navigate(dataDirections)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {
        if (query != null) {
            if (query.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                val filteredList = ArrayList<String>()
                for (i in searchItemList) {
                    if (i.lowercase(Locale.ROOT).contains(query)) {
                        filteredList.add(i)
                    }
                }

                if (filteredList.isNotEmpty()) {
                    adapter.setFilteredList(filteredList)
                }
            } else {
                recyclerView.visibility = View.GONE
            }

        }
    }

    private fun viewModelObserve() {
        searchViewModel.searchItemMLD.observe(viewLifecycleOwner) { product ->
            product?.let {
                searchItemList.addAll(it)
            }
        }
    }
}