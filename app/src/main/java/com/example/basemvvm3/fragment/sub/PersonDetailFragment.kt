package com.example.basemvvm3.fragment.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basemvvm3.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sub_fragment.*

class PersonDetailFragment: DaggerFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sub_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        requireNotNull(arguments).apply {
            val personName = PersonDetailFragmentArgs.fromBundle(this).personName
            text.text = personName
        }
    }
}