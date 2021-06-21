package com.example.crypto.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.crypto.R
import com.example.crypto.databinding.BottomsheetDialogFragmentSortBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetDialog():BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetDialogFragmentSortBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>

    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val sheet = d.findViewById<View>(R.layout.bottomsheet_dialog_fragment_sort)
            behavior = BottomSheetBehavior.from(sheet?.parent as View)
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        setupView()

        return dialog
    }

    private fun setupView() {
        binding.rgSort.setOnCheckedChangeListener { group, checkedId ->
            Toast.makeText(
                context, " On checked change : $checkedId",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}