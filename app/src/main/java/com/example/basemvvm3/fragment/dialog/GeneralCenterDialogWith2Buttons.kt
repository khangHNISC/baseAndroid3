package com.example.basemvvm3.fragment.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.basemvvm3.R
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.general_dialog_with_2_buttons.*

class GeneralCenterDialogWith2Buttons : DaggerDialogFragment() {

    //totally fine to inject vm here
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.general_dialog_with_2_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPositive.setOnClickListener {
            //better way to communicate with prevfragment
            findNavController().previousBackStackEntry?.savedStateHandle?.set("key", "abc")
            findNavController().navigate(R.id.action_generalCenterDialogWith2Buttons_to_navigation_sub_fragment)
        }

        btnNegative.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(
            InsetDrawable(
                ColorDrawable(Color.TRANSPARENT),
                18,
                0,
                18,
                0
            )
        )
    }
}