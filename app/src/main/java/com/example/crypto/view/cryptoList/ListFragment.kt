package com.example.crypto.view.cryptoList

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crypto.AppController.Companion.public_crypto_text
import com.example.crypto.AppController.Companion.public_sort_text
import com.example.crypto.AppController.Companion.public_tag_text
import com.example.crypto.R
import com.example.crypto.adapter.CryptoListAdapter
import com.example.crypto.databinding.FragmentListBinding
import com.example.crypto.model.enums.CryptoType
import com.example.crypto.model.enums.SortDirection
import com.example.crypto.model.enums.SortType
import com.example.crypto.model.enums.TagType
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
    private var sortText: String = SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
    private var sortDirText: String = SortDirection.ASC.toString().toLowerCase(Locale.ROOT)
    private var cryptoTypeText: String = CryptoType.ALL.toString().toLowerCase(Locale.ROOT)
    private var tagText: String = TagType.ALL.toString().toLowerCase(Locale.ROOT)
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var sortCheckedId = 0
    private var cryptoCheckedId = 0
    private var tagCheckedId = 0
    private var isDirAsc = true

    override fun layout(): Int = R.layout.fragment_list

    override val viewModel: CryptoListViewModel by viewModel()
    override fun init() {
        setupOnCLicks()
        setupList()
        setupView()
    }


    private fun setupOnCLicks() {
        binding.btnSort.setOnClickListener { showSortBottomSheetDialog() }
        binding.btnFilter.setOnClickListener { showFilterBottomSheetDialog() }
        binding.imgbtnSortDir.setOnClickListener { setSortDirBtn() }
    }

    private fun setSortDirBtn() {
        if (isDirAsc) {
            binding.imgbtnSortDir.setBackgroundResource(R.drawable.sort_dsc)
            isDirAsc = false
            sortDirText = SortDirection.DESC.toString().toLowerCase(Locale.ROOT)
        } else {
            binding.imgbtnSortDir.setBackgroundResource(R.drawable.sort_asc)
            isDirAsc = true
            sortDirText = SortDirection.ASC.toString().toLowerCase(Locale.ROOT)
        }
        setupView()
    }

    private fun showSortBottomSheetDialog() {
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

    private fun showFilterBottomSheetDialog() {
        bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottomsheet_dialog_fragment_filter)
        val cryptoRadioGroup = bottomSheetDialog?.findViewById<LinearLayout>(R.id.rg_crypto)
        val tagRadioGroup = bottomSheetDialog?.findViewById<LinearLayout>(R.id.rg_tag)
        setupCryptoRadioGroupClick(bottomSheetDialog, (cryptoRadioGroup as RadioGroup))
        setupTagRadioGroupClick(bottomSheetDialog, (tagRadioGroup as RadioGroup))

        bottomSheetDialog?.setOnDismissListener {
            if (public_tag_text != tagText || public_crypto_text != cryptoTypeText) {
                public_tag_text = tagText
                public_crypto_text = cryptoTypeText
                setupView()
                cryptoListAdapter.notifyDataSetChanged()
            }
        }
        bottomSheetDialog?.show()
    }

    private fun setupSortRadioGroupClick(view: BottomSheetDialog?, radioGroup: RadioGroup) {
        if (sortCheckedId != 0)
            radioGroup.check(sortCheckedId)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)
            when (selectedRadioButton?.text.toString()) {
                SortType.MARKET_CAP.sort -> sortText =
                    SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
                SortType.NAME.sort -> sortText =
                    SortType.NAME.toString().toLowerCase(Locale.ROOT)
                SortType.PRICE.sort -> sortText =
                    SortType.PRICE.toString().toLowerCase(Locale.ROOT)
            }
            sortCheckedId = checkedId
        }
    }

    private fun setupCryptoRadioGroupClick(view: BottomSheetDialog?, radioGroup: RadioGroup) {
        if (cryptoCheckedId != 0)
            radioGroup.check(cryptoCheckedId)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)
            when (selectedRadioButton?.text.toString()) {
                CryptoType.ALL.crypto -> cryptoTypeText =
                    CryptoType.ALL.toString().toLowerCase(Locale.ROOT)
                CryptoType.COINS.crypto -> cryptoTypeText =
                    CryptoType.COINS.toString().toLowerCase(Locale.ROOT)
                CryptoType.TOKENS.crypto -> cryptoTypeText =
                    CryptoType.TOKENS.toString().toLowerCase(Locale.ROOT)
            }
            cryptoCheckedId = checkedId
        }
    }

    private fun setupTagRadioGroupClick(view: BottomSheetDialog?, radioGroup: RadioGroup) {
        if (tagCheckedId != 0)
            radioGroup.check(tagCheckedId)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)
            when (selectedRadioButton?.text.toString()) {
                TagType.ALL.tag -> tagText =
                    TagType.ALL.toString().toLowerCase(Locale.ROOT)
                TagType.DEFI.tag -> tagText =
                    TagType.DEFI.toString().toLowerCase(Locale.ROOT)
                TagType.FILESHARING.tag -> tagText =
                    TagType.FILESHARING.toString().toLowerCase(Locale.ROOT)
            }
            tagCheckedId = checkedId
        }
    }


    private fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCryptoList(sortText, sortDirText, cryptoTypeText, tagText)
                .observe(viewLifecycleOwner, {
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