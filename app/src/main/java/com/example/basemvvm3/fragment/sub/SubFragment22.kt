package com.example.basemvvm3.fragment.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.adapter.TagAdapter
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sub_fragment_22.*
import javax.inject.Inject

class SubFragment22 : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: SubFragment22ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = viewModelProvider(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sub_fragment_22, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TagAdapter()
        tagList.adapter = adapter

        vm.tagData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}