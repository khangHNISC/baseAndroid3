package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.activities.MainActivityViewModel
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main_2.*
import javax.inject.Inject

class MainFragment2 : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainActivityViewModel

    private lateinit var vm1: MainFragmentViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = requireActivity().run {
            viewModelProvider(viewModelFactory)
        }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm1.sayHello.observe(viewLifecycleOwner, Observer {
            vm.incrementBy1()
        })

        vm.numberOfClick.observe(viewLifecycleOwner, Observer {
            text.text = it.toString()
        })
    }

    companion object {

        fun newInstance(): MainFragment2{
            return MainFragment2()
        }
    }
}