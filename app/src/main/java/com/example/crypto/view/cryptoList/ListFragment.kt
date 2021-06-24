package com.example.crypto.view.cryptoList

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.R
import com.example.crypto.adapter.CryptoListAdapter
import com.example.crypto.databinding.FragmentListBinding
import com.example.crypto.helper.Constants.Companion.public_crypto_text
import com.example.crypto.helper.Constants.Companion.public_sort_text
import com.example.crypto.helper.Constants.Companion.public_tag_text
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

    companion object {
        private const val START_POSITION = 0
    }

    override fun layout(): Int = R.layout.fragment_list
    override val viewModel: CryptoListViewModel by viewModel()

    lateinit var cryptoListAdapter: CryptoListAdapter
    private var sort: String = SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var sortText: String = SortType.MARKET_CAP.toString().toLowerCase(Locale.ROOT)
    private var sortDirText: String = SortDirection.DESC.toString().toLowerCase(Locale.ROOT)
    private var cryptoTypeText: String = CryptoType.ALL.toString().toLowerCase(Locale.ROOT)
    private var tagText: String = TagType.ALL.toString().toLowerCase(Locale.ROOT)
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var sortCheckedId = 0
    private var cryptoCheckedId = 0
    private var tagCheckedId = 0
    private var isDirAsc = false

    override fun init() {
        setupOnCLicks()
        setupList()
        initData()
        initAdapter()
        bindEvents()
    }

    private fun setupOnCLicks() {
        binding.btnSort.setOnClickListener { showSortBottomSheetDialog() }
        binding.btnFilter.setOnClickListener { showFilterBottomSheetDialog() }
        binding.imgbtnSortDir.setOnClickListener { setSortDirBtn() }
    }

    //for sorting the direction of the list
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
        initData()
    }

    private fun showSortBottomSheetDialog() {
        bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottomsheet_dialog_fragment_sort)
        val sortRadioGroup = bottomSheetDialog?.findViewById<LinearLayout>(R.id.rg_sort)
        setupSortRadioGroupClick(bottomSheetDialog, (sortRadioGroup as RadioGroup))

        bottomSheetDialog?.setOnDismissListener {
            if (public_sort_text != sortText) {
                public_sort_text = sortText
                initData()
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
                initData()
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


    //implement coroutine and crypto viewmodel initialization
    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            this@ListFragment.context?.let {
                viewModel.getCryptoList(it, sortText, sortDirText, cryptoTypeText, tagText)
                    .observe(viewLifecycleOwner, {
                        cryptoListAdapter.submitData(lifecycle, it)
                    })
            }
        }

    }


    //set list adapter
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

    //handling  list scroll, swiperefresh and retry button events
    private fun bindEvents() {
        with(binding) {
            rvCryptoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val scrollPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    refreshLayout.isEnabled = scrollPosition == START_POSITION
                }
            })

            refreshLayout.setOnRefreshListener {
                cryptoListAdapter.refresh()
            }

            btnRetry.setOnClickListener {
                cryptoListAdapter.retry()
            }
        }
    }


    //handling  list loading states and errors
    private fun initAdapter() {
        cryptoListAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && cryptoListAdapter.itemCount == 0
            binding.tvNoResults.isVisible = isListEmpty

            // Only show the list if refresh succeeds.
            binding.rvCryptoList.isVisible = loadState.source.refresh is LoadState.NotLoading

            // Show loading spinner during initial load or refresh.
            handleLoading(loadState.source.refresh is LoadState.Loading)

            // Show the retry state if initial load or refresh fails.
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error

            /**
             * loadState.refresh - represents the load state for loading the PagingData for the first time.
             * loadState.prepend - represents the load state for loading data at the start of the list.
             * loadState.append - represents the load state for loading data at the end of the list.
             * */
            // If we have an error, show a toast
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                showToastMessage(it.error.message.toString())
            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        with(binding) {
            refreshLayout.isRefreshing = loading == true
        }
    }
}