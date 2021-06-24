package com.example.crypto.view.detail

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.crypto.R
import com.example.crypto.databinding.FragmentDetailBinding
import com.example.crypto.view.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

//this fragment shows details of the cryptocurrency composed of item name logo and description
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override fun layout(): Int = R.layout.fragment_detail
    override val viewModel: DetailViewModel by viewModel()


    override fun init() {
        if (arguments != null) {

            //get id of the cryptocurrency for make api call for get detail
            val id = requireArguments().getInt("id")

            getDetailData(id)
            setupView()
        }
    }

    private fun getDetailData(id: Int) {
        lifecycleScope.launch() {
            viewModel.getDetails(id)
        }
    }

    private fun setupView() {

        viewModel.detailData.observe(viewLifecycleOwner) {
            binding.name.text = it.name
            binding.symbol.text = it.symbol
            binding.description.text = it.description
            Picasso.get().load(it.logo).into(binding.imgLogo)
        }
    }
}