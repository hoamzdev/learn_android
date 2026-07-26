package com.example.testBaseApp.ui.common.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.databinding.LayoutBottomsheetDemoBinding
import com.example.testBaseApp.utils.afterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContentBottomSheet(
    //pass by value to here
) : BottomSheetDialogFragment() {
    private var _binding : LayoutBottomsheetDemoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBottomsheetDemoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }

        binding.apply {
            btnPrimaryButton.setOnClickShrinkListener {

            }
            edtFeedback.afterTextChanged {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG = "sheet_content"
    }
}