package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.activities.MainActivityViewModel
import com.example.basemvvm3.fragment.dialog.GeneralBottomDialog
import com.example.basemvvm3.fragment.dialog.GeneralCenterDialogWith2Buttons
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

//share MainFragmentVM with Fragment2 scope activity
class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainVm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVm = requireActivity().run {
            viewModelProvider(viewModelFactory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_dialog_center.setOnClickListener {
            GeneralCenterDialogWith2Buttons.newInstance()
                .show(this.childFragmentManager, CENTER_DIALOG)
        }

        btn_dialog_bottom.setOnClickListener {
            GeneralBottomDialog.newInstance().show(this.childFragmentManager, BOTTOM_DIALOG)
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        mainVm.notifyMainFragment2(childFragment.tag ?: "UNKNOWN")
    }

    companion object {
        private const val CENTER_DIALOG = "center_dialog"
        private const val BOTTOM_DIALOG = "bottom_dialog"
    }
}