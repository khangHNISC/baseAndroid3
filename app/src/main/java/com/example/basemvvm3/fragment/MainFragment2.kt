package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment2 : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm1: MainFragmentViewModel

    private val edit by lazy { arguments?.getBoolean(DATA, false) ?: false }

    private val vm2: MainFragment2ViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                @Suppress("UNCHECKED_CAST")
                return MainFragment2ViewModel(handle) as T
            }
        }
    }

    //1. Call back patter
    interface OnSelectedSthListener {
        fun onSelected()
    }

    private lateinit var callback: OnSelectedSthListener

    fun setOnSelectedListener(callback: OnSelectedSthListener) {
        this.callback = callback
    }

    //somewhere in here will do callback.onSelected()

    //one provide object: OnSelectedSthListener and then MainFragment().setOnSelectedListener(object)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm1 = requireActivity().run {
            viewModelProvider(viewModelFactory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_2, container, false)
    }

    companion object {
        private const val DATA = "DATA"

        fun newInstance(data: Boolean = false): MainFragment2 {
            return MainFragment2().apply {
                arguments = Bundle().apply {
                    putBoolean(DATA, data)
                }
            }
        }
    }
}