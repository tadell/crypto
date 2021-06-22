package com.example.crypto.view.cryptoList

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crypto.AppController.Companion.public_sort_text
import com.example.crypto.R
import com.example.crypto.adapter.CryptoListAdapter
import com.example.crypto.databinding.FragmentListBinding
import com.example.crypto.model.enums.SortType
import com.example.crypto.view.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
 class ListFragment : BaseFragment<FragmentListBinding, CryptoListViewModel>() {
    lateinit var cryptoListAdapter: CryptoListAdapter
    private var sort: String = SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var sortText: String = SortType.MARKET_CAP.toString().toLowerCase()
    private var bottomSheetDialog: BottomSheetDialog? = null

    override fun layout(): Int = R.layout.fragment_list

    override val viewModel: CryptoListViewModel by viewModel()
    override fun init() {
        setupOnCLicks()
        setupList()
        setupView()
    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        binding =
//            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
//
//        return binding.root
//    }


    private fun setupOnCLicks() {
        binding.btnSort.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottomsheet_dialog_fragment_sort)
        val sortRadioGroup = bottomSheetDialog?.findViewById<LinearLayout>(R.id.rg_sort)
        setupSortRadioGroupClick(bottomSheetDialog, (sortRadioGroup as RadioGroup))

        bottomSheetDialog?.setOnDismissListener {
            if (public_sort_text != sortText) {
                public_sort_text = sortText
                setupView()
                cryptoListAdapter.notifyDataSetChanged()
            }
        }
        bottomSheetDialog?.show()
    }

    private fun setupSortRadioGroupClick(view: BottomSheetDialog?, sortRadioGroup: RadioGroup) {
        sortRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)

            when (selectedRadioButton?.text.toString()) {
                SortType.MARKET_CAP.sort -> sortText =
                    SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
                SortType.NAME.sort -> sortText =
                    SortType.NAME.toString().toLowerCase(Locale.ROOT)
                SortType.PRICE.sort -> sortText =
                    SortType.PRICE.toString().toLowerCase(Locale.ROOT)
            }
        }
    }

    private fun setupView(sortText: String) {
//        cryptoListViewModel.sort_text = sortText
//        lifecycleScope.launch {
//            cryptoListViewModel.listData.collect {
//                cryptoListAdapter.submitData(it)
//            }
//        }
    }

    private fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCryptoList(sortText).observe(viewLifecycleOwner, {
                cryptoListAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupList() {
        cryptoListAdapter = CryptoListAdapter(object : CryptoListAdapter.SetOnCryptoClick {
            override fun onCryptoClick(id: Int) {
                val bundle = Bundle()
                bundle.putSerializable("id", id)
                findNavController().navigate(R.id.action_ListFragment_to_DetailFragment, bundle)
            }
        })
        linearLayoutManager = LinearLayoutManager(context)

        binding.lifecycleOwner = this
        binding.rvCryptoList.adapter = cryptoListAdapter
        binding.rvCryptoList.layoutManager = linearLayoutManager
    }

}