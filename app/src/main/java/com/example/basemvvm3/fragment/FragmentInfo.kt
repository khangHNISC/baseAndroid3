package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.activities.MainActivityViewModel
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FragmentInfo : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var manVM: MainActivityViewModel

    private val edit by lazy { arguments?.getBoolean(DATA, false) ?: false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manVM = requireActivity().run {
            viewModelProvider(viewModelFactory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    companion object {
        private const val DATA = "DATA"

        fun newInstance(data: Boolean = false): FragmentInfo {
            return FragmentInfo().apply {
                arguments = Bundle().apply {
                    putBoolean(DATA, data)
                }
            }
        }
    }
}