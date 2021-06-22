package com.example.crypto.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.crypto.R
import com.example.crypto.databinding.FragmentDetailBinding
import com.example.crypto.view.detail.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val id = requireArguments().getInt("id")
            getDetailData(id)
            setupView()

        }

//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_DetailFragment_to_ListFragment)
////        }
    }

    private fun getDetailData(id: Int) {
        lifecycleScope.launch() {
            detailViewModel.getDetails(id)
        }
    }

    private fun setupView() {

        detailViewModel.detailData.observe(viewLifecycleOwner) {
            binding.name.text = it.name
            binding.symbol.text = it.symbol
            binding.description.text = it.description
            Picasso.get().load(it.logo).into(binding.imgLogo)

        }
    }
}