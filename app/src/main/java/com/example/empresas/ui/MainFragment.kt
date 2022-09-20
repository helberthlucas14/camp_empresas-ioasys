package com.example.empresas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.empresas.data.model.Company
import com.example.empresas.R
import com.example.empresas.databinding.FragmentMainBinding
import com.example.empresas.presentation.MainViewModel
import com.example.empresas.presentation.ViewState
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModel<MainViewModel>()
    private val companyAdapter by lazy { CompanyAdapter(::clickItem) }

    private lateinit var homeToolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var txtMessage: TextView
    private lateinit var flipper: ViewFlipper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun startUpToolbar(view: View) {
        homeToolbar = view.findViewById(R.id.toolbarHome)
        txtMessage = view.findViewById(R.id.txtMessage)
        flipper = view.findViewById(R.id.flipper)

        homeToolbar.inflateMenu(R.menu.home_search)
        val searchItem = homeToolbar.menu.findItem(R.id.action_search)

        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.text_query_hint_action_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (companyAdapter.currentList.isNullOrEmpty()) {
                    flipper.displayedChild = FLIPPER_NOTFOUND
                } else {
                    flipper.displayedChild = FLIPPER_SUCCESS
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                companyAdapter.filter.filter(newText)
                flipper.displayedChild = FLIPPER_SUCCESS
                return false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUpToolbar(view)

        binding.recycler.run {
            setHasFixedSize(true)
            adapter = companyAdapter
        }

        setObservers()
    }

    private fun setObservers() {

        mainViewModel.companyListLiveData.observe(viewLifecycleOwner) { viewState ->

            when (viewState.state) {
                ViewState.State.SUCCESS -> {
                    onResultSuccess(viewState.data!!)
                }
                ViewState.State.ERROR -> {
                    onResultError(viewState.error)
                }

                ViewState.State.LOADING -> {
                    onLoading(viewState.isLoading)
                }
            }
        }
    }

    private fun onLoading(loading: Boolean) {
        if (loading)
            flipper.displayedChild = FLIPPER_LOADING
    }

    private fun onResultError(error: Throwable?) {
        Toast.makeText(requireContext(), "${error?.message}", Toast.LENGTH_LONG).show()
    }

    private fun onResultSuccess(list: List<Company>) {
        companyAdapter.setItems(list)
        flipper.displayedChild = FLIPPER_SUCCESS
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getCompanies()
    }

    private fun clickItem(company: Company) {
        findNavController().navigate(
            MainFragmentDirections.actionHomeFragmentToDetailsFragment(
                companyName = company.companyName,
                imageUrl = company.pathImage,
                description = company.description
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val FLIPPER_LOADING = 0
        const val FLIPPER_SUCCESS = 1
        const val FLIPPER_NOTFOUND = 2
    }
}