package com.example.crypto.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crypto.R
import com.example.crypto.adapter.CryptoListAdapter
import com.example.crypto.databinding.FragmentListBinding
import com.example.crypto.viewmodel.CryptoListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    lateinit var cryptoListAdapter: CryptoListAdapter
    private val cryptoListViewModel: CryptoListViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize the view model
        setupList()
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            cryptoListViewModel.listData.collect {
                cryptoListAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        cryptoListAdapter = CryptoListAdapter()
        linearLayoutManager = LinearLayoutManager(context)

        binding.lifecycleOwner = this
        binding.rvCryptoList .adapter = cryptoListAdapter
        binding.rvCryptoList.layoutManager = linearLayoutManager
    }

}